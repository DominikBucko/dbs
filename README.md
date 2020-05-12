# Asset manager
## Zadanie</h1>
Tento projekt slúži na evidenciu assetov (softvérov, hardvérov). Patrí k nemu aj databáza, ktorá je ulozená na servery dbs.fiit.uk.to servery (dbs.fiit.uk.to) a 
obsahuje tabulky rôzne tabulky pre správne ulozenie jednotlivých poloziek.

## Špecifikácia scenárov</h2>
#### Správa použivateľov
Admin pri pridávaní použivateľa do databázy zadáva meno, priezvisko, mesto ,psč, ulicu. Taktiež musí mu vygenerovať meno a heslo. 
Po vykonaní tejto akcie sa v databáze vytvorí záznam o novom použivatelovi a zobrazí sa dialógové okno s informáciou o úspešnom pridaní použivateľa.

Modifikáciu informácií o použivatelovy je realizované zadáním iba zmenenéných údajov adminom. Po vykonaní zmeny sa údaje prepíšu. 

Pre vyhľadanie, postačí, ak sa zadá jeden z atribútov použivateľa.

Admin pre zmazanie použivateĺa musí označiť vyhľadaného pacienta stlačiť tlačidlo zmazať, nasledne potvrdiť voľbu.
Použivateľ nesmie mať ziadne vypožičané assety aby sa z databázy odstránil záznam o použivateľovi, ak to tak není zobrazí sa dialogove okno o oznameni 
neodstraneni použivateľa z dôsledku aktivných vypožičaní, ak použivateľ nema aktivne vypožičania tak sa zobrazí okno s informáciou o uspešnom zmazaní pacienta.

Zobrazenie použivateľa je vyriešene rozkliknutím vyhľadaného pacienta. Po rozkliknutí sa otvorí okno s informáciami o použivatelovia a zoznamom vypožičaných assetov.

#### Vyradenie assetu
Admin pre vyradenie assetu z databázy si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo zmazať a potvrdí voľbu.
Asset musí byť voľný, ak je tak sa zobrazí okno o potvrdení odstránenia assetu, inak sa zobrazí okno o nezmazaní assetu z databazy z dôsledku aktivného vypožičania.


#### Pridanie assetu
Admin pri pridávaní assetu do databázy zadáva názov, typ, vyberie kategóriu a oddelenie a vygeneruje QR kód, stav sa automatický nastaví na voľný.
Po vykonaní tejto akcie sa v databáze vytvorí zaznam o novom assete a zobrazí sa dialógové okno s informáciaciou o úspešnom pridaní assetu.

#### Presun assetu na ine oddelenie
Admin pre presun assetu na ine oddelenie si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo presunuť asset. 
Zobrazí sa mu okno kde vyberie oddelenie kam chce asset presunúť a klikne na tlačidlo presunuť. Asset musí byť voľný, ak je tak  sa zobrazí okno o potvrdení presunutia assetu na oddelenie,
inak sa zobrazí okno o nepridaní assetu na oddelenie z dôsledku aktivného vypožičania.
 
#### Vydanie assetu na servis
Admin pre zaradenie assetu do servisu si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo odoslať na servis. 
Vyplní komentár k chybe a klikne na tlačidlo odoslať. Asset nemože byť vypožičaný, ak je tak sa zobrazí okno o nepridaní assetu na servis z dôsledku aktivného vypožičania, inak sa zobrazí okno o potvrdení pridania assetu na servis.

#### Požiadavka o pridelenie a vrátenie assetu
Použivateľ stlačí tlačidlo pre požiadanie o asset, kde si vyberie, ktorý asset potrebuje, nasledne ho označí a klikne na tlačidlo požiadať. 
Potom môže ale nemusí vyplniť komentár a stači tlačidlo požiadať. Požiadavka sa pridá do tabuľky ticket a použivateľovi sa zobrazí okno o úspešnom registrovaní požiadavky.

#### Schválenie požiadavky pre pridelenie a vratenie assetu
Admin označí požiadavku ktorú chce potvrdiť a nasledne stlačí tlačidlo schváliť, zobrazí sa mu okno o overení schvalenia, ktorú ak potvrdí, zobrazí sa mu okno o úspešnom schválení požiadavky.

Použivateľ po úspešnom schvalení si môže vyzdvihnuť alebo vrátiť asset.

### Logický model</h3>
![Logicky model](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/logicky_model.jpg "Logicky model")

Obsahuje osem tabuliek. Sú v ňom pridané kardinality medzi jednotlivými tabuľkami.

### Fyzický model</h3>
![Fyzicky model](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/fyzicky_model.png "Fyzicky model") 

Obsahuje desať tabuliek. Každá tabuľka má primárny klúč. Su vnej rozbité vztahy m:n

## Opis návrhu a implementácie</h2>

### Programátorské prostredie</h3>

Na vývoj grafikej časti sme použili framework Vaadin, ktorý komunikuje s aplikáciou pomocou Springu. Použili sme databázu postgresSQL,
ktorú sme vytvorili v pgAdmin4 cez konzolu.  Prepojenie a samotnú aplikáciu sme vytvárali v Jave v prostredí InteliJ IDEA. Na naplnenie 
databázy sme si vytvorili PYTHON script s kniznicou Faker, ktorý nam vygeneroval Query.

### Návrhové rozhodnutia</h3>

Databáza je uložená na servery od DigitalOcean. Na spojenie s databázou použivame Connector Service.

### Opis implementácie jednotlivých scenárov</h3>

![Login page](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/login.png "Okno pre prihlasenie")

### Správa použivateľov</h3>
![Zobrazovanie použivateľov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/users_getAll.png "Okno pre zobrazenie použivateľov") 
#### Pridanie použivatela</h3>
Pri pridávani nového použivatela do databázy admin zadá položky. Jednotlivé položky sú ošetrene, aby sa do databázy uložili iba validné údaje. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu ako napríklad SQL Injection.

Ukážka naplňania:
```
 INSERT INTO asset_manager.public.user (first_name, surname, city, address, postcode, user_department, login, password, is_admin)
 VALUES (:fname, :surname, :city, :address, :postcode, :user_department, :login, :password, :is_admin)
```
![Pridavanie použivateľa](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/create_user.png "Okno pre pridanie použivateľa") 


#### Mazanie použivatela</h3>
Taktiež ako pri naplňaní tak aj pri mazaní sme použili parametrové ukladanie vstupu.

Ukážka zmazania:
```
DELETE FROM "user" WHERE user_id = selected_user_id
```
![mazanie/upravovanie použivateľa](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/edit_delete_user.png "Okno pre mazanie/upravovanie použivateľa")

#### Aktualizovanie použivatela</h3>
Pri modifikácii použivateľa zadá admin iba zmenené údaje do formuláru podobného ako pri naplňani. Po vykonaní tohto scenáru sa zmeny uložia na server.  

Ukážka aktualizovania:
```
UPDATE asset_manager.public.user
SET first_name = :fname, surname = :surname, city = :city, address = :address, postcode = :postcode, user_department = :user_department, login = :login, password = :password, is_admin = :is_admin
WHERE user_id = :id
```

![mazanie/upravovanie použivateľa](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/edit_delete_user.png "Okno pre mazanie/upravovanie použivateľa") 

#### Filtrovanie použivateľov</h3>

Vyhľadanie použivateľov možno podľa vybraného atribútu a nasledne aj textu, ktorý funguje ako regex.

```
SELECT user_id, first_name, surname, city , address, postcode, user_department, login, password, is_admin, department_name, count(user_info) from \"user\"
INNER JOIN department d on user_department = d.department_id
LEFT JOIN ticket t on user_id = t.user_info
WHERE (lower(property)) LIKE lower(selected_property)
AND time_returned IS NOT NULL
GROUP BY user_id, department_name
ORDER BY count(user_info) DESC
```
![Filtrovanie použivateľa](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/user_filter_zlozita_query_1.png "Okno pre filtrovanie použivateľa") 

### Zobrazenie assetov</h3>
Zobrazenie assetov spolu s menom oddelenia, kde patria.

Ukážka zobrazovania:
```            
SELECT * FROM asset_manager.public.asset
INNER JOIN asset_manager.public.department
ON asset.asset_department = department.department_id
```
![Zobrazenie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/assets_zobrazenie.png "Okno pre zobrazenie assetov") 

#### Filtrovanie assetu
 Netrivialne query, zobrazuje assety, ktore je mozne vyfiltrovat podla parametrov
```
SELECT "name", "type", status, department_name, count(*) as "SUM" from asset
JOIN department on asset_department = department_id
WHERE department_name LIKE selected_department AND status LIKE selected_status
GROUP BY "name","type", status, department_name
HAVING type = selected_type
ORDER BY "SUM" DESC
```
![Filtrovanie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/dashboard_zlozita_query_2.png "Okno pre filtrovanie assetov") 


![Filtrovanie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/asset_filter.png "Okno pre filtrovanie assetov") 

### Mazanie assetov
Po otvorení detailu assetu a stlačení tlačidla Delete sa spustí nasledujúca časť kódu

Ukážka mazania:
```
DELETE FROM asset WHERE asset_id=selected_asset
```
![Odstranenie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/asset_editovanie_vymazanie.png "Okno pre odstranenie assetov") 

### Pridavanie assetov
Pri pridávani nového assetu do databázy admin zadá položky. Jednotlivé položky sú ošetrene,
aby sa do databázy uložili iba validné údaje. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu ako napríklad SQL Injection.

Ukážka pridavania:
```
INSERT INTO asset_manager.public.asset ("name", "type", qr_code, asset_category, asset_department, status)
VALUES (:name, :type , :qr_code, :category, :department, :status)", parameterSource );
```

![Pridavanie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/asset_vytvorenie.png "Okno pre pridanie assetov") 

### Upravovanie assetov
V tomto scenári sa zobrazí okno a upravia sa atributy, ako aj oddelenie. Po kliknutí na tlačidlo save sa vykoná query a zmeny sa uložia na server.

Ukážka upravovania:
```
UPDATE asset_manager.public.asset
SET "name" = :name, "type" = :type, qr_code = :qr_code, asset_category = :category, asset_department = :department, status = :status
WHERE asset_id = :index
```
![Upravovanie assetov](https://github.com/FIIT-DBS2020/project-balucha_bucko/blob/master/screenshot_scenare/asset_editovanie_vymazanie.png "Okno pre upravenie assetov") 

### Vydanie assetu na servis
Pri vydaní assetu na servis admin klikne v ľavom paneli na Faults a následne klikne na Register new fault, kde po vyplnení id assetu sa automaticky vyplnia údaje o assete, potom
označi o aku chybu ide a nastaví atribut is fixable pre identifikáciu, či asset je možné opraviť. Po kliknutí sa spustí transakcia, ktorá zabezpečí, aby sa pridal záznam do 
do tabulky asset_fault a nastavil sa assetu atribut status na "IN SERVICE" 

Ukážka vydania:
```
SAVEPOINT "SAVE"

// QUERY1
UPDATE asset_manager.public.asset
SET status = 'IN SERVICE'
WHERE asset_id = selected_asset_id ;

// QUERY2
INSERT INTO asset_fault(fault_id, asset_id, time_of_failure, fixable)
VALUES (selected_fault_id, selected_asset_id, time_now, selected_tag);

COMMIT
```
Ak nam nezbehne jeden z query tak sa vrati na savepoint:

```
ROLLBACK(SAVE)
```

### Vybratie assetu zo servisu
Pri vybratí assetu zo servis admin vyberie položku z faults, zobrazi sa mu okno o potvrdení vrátenia assetu zo servisu. Po potvrdení sa spustí transakcia, ktorá zabezpečí, aby sa odstránil záznam z 
tabulky asset_fault a nastavil sa assetu atribut status na "FREE" 

Ukážka vydania:
```
SAVEPOINT "SAVE"

// QUERY1
UPDATE asset
SET status = 'IN FREE'
WHERE asset_id = selected_asset_id ;

// QUERY2
UPDATE asset_fault
SET fix_time = date_now
WHERE asset_fault_id = selected_asset_fault

COMMIT
```
Ak nam nezbehne jeden z query tak sa vrati na savepoint:

```
ROLLBACK(SAVE)
```

### Požiadavka o pridelenie a vrátenie assetu


### Schválenie požiadavky pre pridelenie a vratenie assetu

