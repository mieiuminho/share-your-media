USE ShareYourMedia;

TRUNCATE TABLE REGULAR_USERS;
TRUNCATE TABLE ADMIN_USERS;

# PASSWORD = shareyourmedia
INSERT INTO REGULAR_USERS (email, name, password, salt)
VALUES ('hugo@uminho.pt', 'Hugo Carvalho',
        'Goe4bJxAdwdRLd0T/NEsRYzrKb9gg1kLJUcNUthH/DqXGUzexDgau6CfvG1KB2oAi5QgmbpkwRo4zd5Nf6RwpQ==', 'ptlchzsj');

INSERT INTO ADMIN_USERS (email, name, password, salt)
VALUES ('hugo@uminho.pt', 'Hugo Carvalho',
        '8XyhkreXfGh7FOOWg8h5v+KiyqqZwOBw5xpHRsQKMLgdzSoWM6z3nTtZRV/ed+R4dDzwnY2RvhnQ4a/5k9KkFw==', 'prrgcsxk');

INSERT INTO REGULAR_USERS (email, name, password, salt)
VALUES ('nelson@uminho.pt', 'Nelson Estevão',
        'MbSDSO/ukiT7djXf/kKT4QKoeqD/MdYwlKBOP5GkWXiT2oxDQljuGRF2wPtDKYMYEqK19R8ia0qp03lKG5c+Xw==', 'vsoptxmt');

INSERT INTO ADMIN_USERS (email, name, password, salt)
VALUES ('nelson@uminho.pt', 'Nelson Estevão',
        'G78i/K4SRI9R6Z1lgh2vdKP2byDhNZCrBBxt0zS+CTeD2YjPTNvTn12Ii71jMkMeW3CEgPzN8GfQtXcGbixjAg==', 'jnsuvqfj');

INSERT INTO REGULAR_USERS (email, name, password, salt)
VALUES ('pedro@uminho.pt', 'Pedro Ribeiro',
        't1x8khsvmlT5i/SEKZYTZdP4v0zUGAGm1UFJEXE8v60UNx3BVa38sRyhVKMcgfZTPbp9TrAF+LW04aGToQZCjg==', 'qmxcpbzl');

INSERT INTO ADMIN_USERS (email, name, password, salt)
VALUES ('pedro@uminho.pt', 'Pedro Ribeiro',
        'hhJJwDTeZ6cEuLSKkn20X7uLCea+DhSwpATKDXTaecpCE8WTFFkeIuizVe36erqQ+Y2pkAFQjivLJExRfPYUcg==', 'hdhloess');

INSERT INTO REGULAR_USERS (email, name, password, salt)
VALUES ('rui@uminho.pt', 'Rui Mendes',
        'w/yXEFZ0d4ehpPlJbfZ92wbm6ePPLYbcIVHRCfQOmGX7H6atSxFJxCxCAs9Eh0lbz8DsL/6GJLj2N/JZ+vvyyA==', 'smixgtkb');

INSERT INTO ADMIN_USERS (email, name, password, salt)
VALUES ('rui@uminho.pt', 'Rui Mendes',
        'CUtbVkx83xILgZrLr+sjSnwv9m7n5gj4NBBhqHp7gNfDg8g5SWmLh7QwVdvzyLdNPPQB85/qiopXUUv9uogp+g==', 'thsfqvvj');

INSERT INTO REGULAR_USERS (email, name, password, salt)
VALUES ('ricardo@uminho.pt', 'Ricardo Silva',
        'ZRh8sG9Zk6NK1gVnIzyehZxwks2NwrVsBtsTSmEw2FExzKjfinWXSeFEXcR2uMffG+f298kOTyr8qY1CTMM9Mw==', 'eppccipb');

INSERT INTO ADMIN_USERS (email, name, password, salt)
VALUES ('ricardo@uminho.pt', 'Ricardo Silva',
        'mKObJ1CxEVGXcgVY1ff5jq53SHnM392bd34MtpA5nt86gJfPkVQY3eYd8D45iSDwIGjjCqOAU1QqRszXttHs0w==', 'sqfkgrsg');

INSERT INTO MEDIAFILES (name, artist)
VALUES ('Born To Die', 'Lana Del Rey');

select *
from REGULAR_USERS;

select *
from ADMIN_USERS;

select *
from MEDIAFILES;


