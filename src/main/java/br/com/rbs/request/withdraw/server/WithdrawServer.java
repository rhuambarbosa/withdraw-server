package br.com.rbs.request.withdraw.server;

import br.com.rbs.request.withdraw.dto.ResponseDto;
import br.com.rbs.request.withdraw.service.WithdrawService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WithdrawServer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawServer.class);

    @Autowired
    private WithdrawService withdrawService;

    @Value("${port}")
    private int port;

    @Value("${thread.pool}")
    private int threadPool = 20;

    @Override
    public void run(String... args) throws Exception {
        try (ServerSocket listener = new ServerSocket(port)) {
            ExecutorService pool = Executors.newFixedThreadPool(threadPool);
            while (true) {
                pool.execute(new RequestTransaction(listener.accept()));
            }
        }
    }

    private class RequestTransaction implements Runnable {
        private Socket socket;

        RequestTransaction(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                LOGGER.info("WithDrawServer-WithdrawService:Connected:{}", socket);

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = br.readLine();

                Instant start = Instant.now();
                ResponseDto responseDto = withdrawService.withdraw(message);

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(new Gson().toJson(responseDto));
                oos.close();
                br.close();
                socket.close();

                LOGGER.info("WithDrawServer-WithdrawService:Mensagem processada em {}ms. authorizationCode={}",
                        Duration.between(start, Instant.now()).toMillis(),
                        responseDto.getAuthorization_code());
            } catch (Exception e) {
                LOGGER.info("WithDrawServer-WithdrawService:Error:{}", socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                LOGGER.info("WithDrawServer-WithdrawService:Closed:{}", socket);
            }
        }
    }
}