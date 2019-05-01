package br.com.rbs.request.withdraw.service;

import br.com.rbs.request.withdraw.exception.InvalidAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class PsiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PsiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.psi.accounts}")
    private String urlPsiAccount;

    public String getAccount(Long cardNumber, String authorizationCode) throws Exception {
        ResponseEntity<String> response;

        try {
            LOGGER.info("WithDrawServer-PsiService:Montando busca da conta. authorizationCode={}", authorizationCode);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPsiAccount)
                    .queryParam("cardNumber", cardNumber)
                    .queryParam("enable", true);

            LOGGER.info("WithDrawServer-PsiService:Buscando conta authorizationCode={}", authorizationCode);
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, generateEntityToSend(), String.class);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                LOGGER.info("WithDrawServer-PsiService:Conta encontrada authorizationCode={}", authorizationCode);
                return response.getBody();
            }
        } catch (Exception e) {
            LOGGER.info("WithDrawServer-PsiService:Erro ao recuperar conta athorizationCode={}", authorizationCode, e);
            throw new Exception(e);
        }

        LOGGER.info("WithDrawServer-PsiService:Conta não encontrada authorizationCode={}", authorizationCode);
        throw new InvalidAccountException("Account not found");
    }

    private HttpEntity<String> generateEntityToSend() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }
}