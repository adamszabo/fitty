INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', 'Stoplis cipő, füves pályára. Jó minőségű füves pályára ajánljuk. Felsőrész anyaga: a legjobb minőségű kenguru bőr', 'Adidas', 'F50 ADIZERO', 44990;
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', 'A Givova Simple Box tartalma: 
- utazó melegítő
- edző melegítő
- esőkabát
- téli kabát
- mez set
- téli sapka
- sportszár
- cipőtartós utazótáska', 'Givova', 'Givova Silver box', 29990;
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', 'Tartozékai:
-tömlő autószelepes gyors-csatlakozóval
-2 db különböző méretű fúvó fej (matracokhoz, medencékhez)
-szeleptű', 'Winner', 'Kompresszor', 29990;
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', 'Fém merevítővel', 'Select', 'Select térdvédő', 26990;
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', 'fekete SM(pici)', 'BodyFit', 'Rod egyenes nadrág', 3990;
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', '', 'Everlast', 'Everlast Maximo trikó', 4160;  
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    select nextval('hibernate_sequence'), '2013-02-08', '50x8', 'Brutal Nutrition', 'Brutal Csukóbandázs', 2490;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'fekete', 'Spartan', 'Crossfit bandázs', 1290;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Több színű', 'Mad Max', 'Sandwich-öv', 4940;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Szürke', 'GymStar', 'Extra Support öv', 2175;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', '', 'Omron', 'Omron MX vérnyomásmérő', 16000;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Rózsaszín', 'Polar', 'Polar F11 pulzusmérő', 34440;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Fekete', 'Polar', 'Polar FS2c', 15900;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', '', 'Accu Measure', 'Fat Track Gold Premium zsírszintmérő', 15990;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Bicepszezésre szolgáló hajlított rúd (francia rúd). Menetes rúdvég, csillag alakú menetes szorítóval. 130 cm hosszú. Súly 12 kg.', 'Egyéb', 'Krómozott franciarúd', 7180;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Krómozott egykezes súlyzópár. Szép kivitelű krómozott súlyzók. Csak párban kaphatók.', 'Ruilin Rising', 'Krómozott súlyzó 2x 4kg', 5800;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'A Powerball "alap" változata, melyre akár utólag is felszerelhető a digitális fordulatszámmérő. Kiválóan alkalmas arra, hogy eddze az ujjakat, a csuklót, illetve a kar izmait.', 'PowerBall', 'Powerball 250Hz', 5690;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Az egyik leghatéknyabb hasizom erősítő eszköz. Jól használva nagyon nehéz vele végezni a gyakorlatot, ám igazán mély és szeparált hasizmokat lehet vele építeni.', 'Everlast', 'Hasizom kerék', 3090;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', '4 sebességes masszírozógép. Edzett üvegtalp. Króm állvány. 3 masszírozószalag.', 'Stoci', 'S4 masszírozó gép', 34940;
INSERT INTO product(
        id, creation, details, manufacturer, name, price)
	select nextval('hibernate_sequence'), '2013-02-08', 'Intenzív otthoni tornára alkalmas stepper. Szabadalmaztatott ellenállásszabályzó rendszere lehetővé teszi az ellenállás módosítását az edzés bármely szakaszában.', 'insportline', 'Forte stepper', 26370;

	
INSERT INTO membershiptype(
            id, detail, expiredateindays, isintervally, maxnumberofentries, 
            price)
    select nextval('hibernate_sequence'), '1 alkalomas', 0, false, 1, 1690;
INSERT INTO membershiptype(
            id, detail, expiredateindays, isintervally, maxnumberofentries, 
            price)
    select nextval('hibernate_sequence'), '10 alkalmas', 0, false, 10, 12990;
INSERT INTO membershiptype(
            id, detail, expiredateindays, isintervally, maxnumberofentries, 
            price)
    select nextval('hibernate_sequence'), '1 hónap', 30, true, 0, 18900;
INSERT INTO membershiptype(
            id, detail, expiredateindays, isintervally, maxnumberofentries, 
            price)
    select nextval('hibernate_sequence'), '3 hónap', 90, true, 0, 53900;
    

    
INSERT INTO fitness_user(
            id, email, enabled, fullname, lastlogin, lastloginip, mobile, 
            numberofretries, password, registration, username)
    select nextval('hibernate_sequence'), 'tamas@fitty.hu', true, 'Bihari Tamás', '2013-02-08', '', '+3630332446', 0, 'ed2e000f726872ed551d79ce49280cf7bcd2d69c28ac07ec296f62c1b1ec3ebcae4cf4c106ecee85', '2013-02-08', 'tomi';
INSERT INTO role(
            id, name, user_id)
    select nextval('hibernate_sequence'), 'Trainer', id from fitness_user where username = 'tomi';
INSERT INTO fitness_user(
            id, email, enabled, fullname, lastlogin, lastloginip, mobile, 
            numberofretries, password, registration, username)
    select nextval('hibernate_sequence'), 'marci@fitty.hu', true, 'Sereg Márton', '2013-02-08', '', '+3630332423', 0, 'ed2e000f726872ed551d79ce49280cf7bcd2d69c28ac07ec296f62c1b1ec3ebcae4cf4c106ecee85', '2013-02-08', 'smarci';
INSERT INTO role(
            id, name, user_id)
    select nextval('hibernate_sequence'), 'Trainer', id from fitness_user where username = 'smarci';