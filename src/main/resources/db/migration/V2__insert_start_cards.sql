INSERT INTO public.account_psi
(card_number, account_id, "enable", creation_date)
VALUES(1234567890123456, 'a4b56dd81717add5', true, now());

INSERT INTO public.account_psi
(card_number, account_id, "enable", creation_date)
VALUES(1234567774523456, '84d6467d1f7b73db', true, now());

INSERT INTO public.account_psi
(card_number, account_id, "enable", creation_date)
VALUES(1234567899865034, '4c1125ae541b405b', true, now());

INSERT INTO public.account_psi
(card_number, account_id, "enable", creation_date)
VALUES(7450317890123456, '97924ba189238728', true, now());

INSERT INTO public.account
(account_id, available_amount, block_balance, creation_date)
VALUES('a4b56dd81717add5', 1000.00, false, now());

INSERT INTO public.account
(account_id, available_amount, block_balance, creation_date)
VALUES('84d6467d1f7b73db', 1000.00, false, now());

INSERT INTO public.account
(account_id, available_amount, block_balance, creation_date)
VALUES('4c1125ae541b405b', 2000.00, false, now());

INSERT INTO public.account
(account_id, available_amount, block_balance, creation_date)
VALUES('97924ba189238728', 500.00, false, now());