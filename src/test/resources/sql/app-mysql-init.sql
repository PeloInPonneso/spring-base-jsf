INSERT INTO `springbase`.`USER` (`ID`, `ACCOUNT_NON_EXPIRED`, `ACCOUNT_NON_LOCKED`, `CREDENTIALS_NON_EXPIRED`, `ENABLED`, `PASSWORD`, `USERNAME`, `EMAIL`) VALUES (1, b'1', b'1', b'1', b'1', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 'admin@springbase.com');
INSERT INTO `springbase`.`ROLE` (`ID`, `AUTHORITY`) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
INSERT INTO `springbase`.`USER_ROLE` (`USER_ID`, `ROLE_ID`) VALUES (1, 1), (1, 2);