insert into users (id, addressDataSet_id, name, login, password)
           values (1, 1, 'user1', 'login1', 'login1'),
                  (2, 2, 'user2', 'login2', 'login2'),
                  (3, 3, 'user3', 'login3', 'login3'),
                  (4, 4, 'user4', 'login4', 'login4'),
                  (5, 5, 'user5', 'login5', 'login5'),
                  (6, 6, 'user6', 'login6', 'login6'),
                  (7, 7, 'user7', 'login7', 'login7'),
                  (8, 8, 'user8', 'login8', 'login8'),
                  (9, 9, 'user9', 'login9', 'login9'),
                  (10, 10, 'Алекcандр', 'Admin', 'Admin')
;

insert into address (id, street)
             values (1, 'Address1'),
                    (2, 'Address2'),
                    (3, 'Address3'),
                    (4, 'Address4'),
                    (5, 'Address5'),
                    (6, 'Address6'),
                    (7, 'Address7'),
                    (8, 'Address8'),
                    (9, 'Address9'),
                    (10, 'Address')
;

insert into phones (id, user_id, number)
           values  (1, 1, 'phone1'),
                   (2, 2, 'phone2'),
                   (3, 3, 'phone3'),
                   (4, 4, 'phone4'),
                   (5, 5, 'phone5'),
                   (6, 6, 'phone6'),
                   (7, 7, 'phone7'),
                   (8, 8, 'phone8'),
                   (9, 9, 'phone9'),
                   (10, 10, 'phone10')
;