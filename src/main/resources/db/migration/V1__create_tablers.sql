CREATE TABLE account (
	account_id varchar(255) PRIMARY KEY,
	available_amount numeric(10,2) NOT null default 0.0,
	block_balance BOOL NOT null default false,
	creation_date TIMESTAMP NOT NULL default now()
);

CREATE TABLE transaction (
	authorization_code varchar(255) PRIMARY KEY,
	account_id varchar(255),
	action varchar(255),
	code varchar(255),
	amount numeric(10,2),
	creation_date TIMESTAMP NOT NULL
);
CREATE INDEX idx_account_action ON transaction (account_id, action);

CREATE TABLE account_psi (
	card_number numeric PRIMARY KEY,
    account_id varchar(255) NOT NULL,
	enable BOOL NOT null default true,
	creation_date TIMESTAMP NOT NULL default now()
);
CREATE INDEX idx_card_enable ON account_psi (card_number, enable);