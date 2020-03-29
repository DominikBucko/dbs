# Databáza pre obvodného lekára na evidenciu pacientov #

## Zadanie</H1>
Táto databáza slúži pre obvodného lekára na evidenciu pacientov, ich zdravotných problémov a predpisaných liekov. Obsahuje číselnik pacientov s informáciami ako bydlisko, kontaktné údaje, informácie o poistovni. Taktiež obsahuje zoznam chorôb s popisom choroby a možnostami liečenia.

## Špecifikácia scenárov</h2>
### Správa pacientov </h3>

Používateľ pri pridávaní pacienta do databázy zadáva meno, priezvisko, rodné číslo, psč, miesto bydliska, jeho telefon a email. Taktiež musí zadať miesto narodenia a kód zdravotnej poisťovne. Po vykonaní tejto akcie sa v databáze vytvorí záznam o novom pacientovi a spustí sa dialógové okno s informáciou o úspešnom pridaní daného pacienta.

Používateľ pri zmazaní pacienta musí označiť vyhľadaného pacienta jedným klikom na záznam a stlačiť tlačidlo zmazať. Po vykonaní tohto scenáru sa z databázy odstráni zaznam o danom pacientovi.Po vykonaní akcie sa otvori dialogové okno s informáciami o uspešnom zmazaní pacienta.

Pri modifikácií informácií o pacientovi zadá doktor iba zmenené údaje. Po vykonaní zmeny sa údaje prepíšu a pri následnom zobrazení uvidí doktor už nové údaje. 

Pri vyhľadaní pacienta, postačí, ak doktor zadá či už meno, priezvisko alebo rodné číslo.

Zobrazenie pacienta je realizované dvojitým kliknutím na vyhľadaného pacienta. Po kliknutí sa otvorí nové okno s informáciami o pacientovi.


### Správa liekov </h3>
Doktor pri pridávaní lieku do databázy zadáva názov, kod, výrobcu, predajcu a ceny, ktorú zaplatí pacient a poistovňa. Po vykonaní tejto akcie sa v databáze vytvorí zaznam o novom lieku a spustí sa dialógové okno s informáciaciou o úspešnom pridaní daného lieku.

### Správa očkovaní </h3>
Doktor pri pridávaní očkovania do databázy zadáva názov a popis daného očkovania. Po vykonaní tejto akcie sa v databáze vytvorí záznam o novom očkovaní. 

Zobraznie očkovania nám zobrazí informacie o očkovaní, čiže názov a popis ako prebehlo očkovanie. 


### Správa chorôb</h4> 

Doktor pri pridávaní choroby do databázy zadáva názov, kód a popis danéj choroby. Po vykonaní tejto akcie sa v databáze vytvorí zaznam o novej chorobe.

Pri nejednoznačnom výbere diagnozy si može doktor vyhľadať danú chorobu a prečítať si jej príznaky.



## Fyzický model</h2>
![Fyzický model](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/ER_Diagram.png?token=AetJRwHNYbC3TgS5Nsd3cB534AKKiiaaks5csu_7wA%3D%3D "Fyzicky model")

Fyzický model obsahuje desať tabuliek, z toho tri číselníky. Každá tabuľka obsahuje primárny kľúč. V tomto diagrame sa nachádzajú tri väzobné tabuľky. 


## Logický model</h2>
![Logický model](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/Logical_Diagram.png?token=AetJRzDv8v5ZQdV42uJNVwcNJpCA2lFSks5csu__wA%3D%3D "Logický model")

Logický model obsahuje sedem tabuliek, z toho tri číselníky. Sú v ňom pridané kardinality medzi jednotlivými tabuľkami. Sú z neho odstránene väzobné tabuľky.


## Opis návrhu a implementácie</h2>

### Programátorské prostredie</h3>

Na vývoj grafikej časti sme použili Scene Builder. Použili sme platformu JavaFX.
Pri vytváraní databázi sme pracovali v prostredí JetBrains DataGrip. Použili sme postgresSQL.
Prepojenie sme riešili v InteliJ IDEA Community Edition. Na riešenie sme implementovali programovací jazyk Java.

### Návrhové rozhodnutia</h3>

Databáza je vytvorená na localhost servery a pripájame sa k nej ako klienti. Po vykonaní každého scenára ukončíme spojenie so serverom. 

### Opis implementácie jednotlivých scenárov</h3>
### Správa pacientov</h3>
#### Pridanie pacientov</h4>
Pri pridávani nového pacienta do databázy musí doktor zadať všetky položky. Jednotlivé položky sú ošetrene, aby sa do databázy uložili iba validné údaje. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu ako napríklad SQL Injection.

Ukážka naplňania:
```mySQL
String SQL = "INSERT INTO amb.pacient(meno, priezvisko, rodne_cislo, psc, mesto, ulica, telefon, email, miesto_narodenia, kod_zdravotnej_poistovne) " + "VALUES('Milan', Potocny, '9063250950', '09025', 'Trnava', 'Bernolakova 10', '0945123321', 'mino123@gmail.com', 'Trnava', '' )";
```

Ukážka grafického rozhrania

![Pridanie pacienta dialog](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridaniePacienta.png?token=AetJRwFqhzSxSaqdYLlEeD1BKx2qyU2bks5csvLBwA%3D%3D "Pridanei pacienta")

Ukážka grafického okna pre nezadané vstupné parametre

![Pridanie pacienta](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/ErrorOkno.png?token=AetJR0OhHu4vVJsci_ckj2siq5-2jGqtks5csxcuwA%3D%3D "Pridanei pacienta dialog")


#### Mazanie pacientov</h4>
Taktiež ako pri naplňaní tak aj pri mazaní sme použili parametrové ukladanie vstupu.

Ukážka zmazania:
```mySQL
String SQL = "DELETE FROM amb.pacient WHERE priezvisko = 'Hermiston' AND rodne_cislo = '4138632778'";
```

Ukážka grafického rozhrania

![Zmazanie pacienta](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/zmazaniePacienta.png?token=AetJR_CmZATKcOtL4URfAEm2NE5kGLMhks5csvNdwA%3D%3D "Zmazanie pacienta")

#### Modifikacia pacientov</h4>
Pri modifikácii pacienta zadá doktor iba zmenené údaje do formuláru podobného ako pri naplňani. Po vykonaní tohto scenáru sa zmeny uložia na server. Može modifikovať aj viacero záznamov naraz v tabuľke.  

Ukážka modifikácie:
```mySQL
String SQL = "UPDATE amb.pacient SET mesto = 'Dushanbe' WHERE id_pacient = 25568;"
```

Ukážka grafického rozhrania

![Upravit pacienta](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/upravitPacienta.png?token=AetJR7IKurlwRHuQXr7v-wYA2ZjjIte5ks5csvkmwA%3D%3D "Upravit pacienta")

#### Vyhľadanie pacientov</h4>

Vyhľadanie pacientov využíva tri druhy indexovania a to meno, priezvisko a rodné číslo. Vďaka indexovaniu vieme v rýchlejšie vyhľadávať pacientov v našej databáze.

```mySQL
String SQL = "SELECT * FROM amb.pacient WHERE ((rodne_cislo || ' ' || meno || ' ' || priezvisko) LIKE 'Ro%') OR ((rodne_cislo || ' ' || priezvisko) LIKE 'Ro%') OR (( meno || ' ' || priezvisko) LIKE 'Ro%' ) OR (( priezvisko) LIKE 'Ro%' ) LIMIT 100";
```

Ukážka grafického rozhrania

![Vyhladaj pacienta](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/vyhladajPacienta.png?token=AetJR2ErQ5168Bc3HRdnaYLgQavNHHwoks5csvnVwA%3D%3D "Vyhladaj pacienta")

### Zobrazenie pacientov</h3>

Zobrazenie pacientov predstavuje detailnejší pohľad na každého pacienta zvlášť. Z tohto okna vieme dalej pristupovať k dalším scenárom.

```mySQL
String SQL = "SELECT * FROM amb.pacient WHERE id_pacient = 42355";
```

Ukážka grafického rozhrania

![Zobraz pacienta](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/zakladneInfoPacient.png?token=AetJRzWNG7Ri0T3OUnPm4BFk6oGqlC_cks5csvn5wA%3D%3D "Zobraz pacienta")

### Správa liekov </h3>
#### Pridanie liekov</h4>

Pri pridávaní nového lieku do databázy musí používateľ zadať všetky položky.Pri naplňaní ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu. 

```mySQL
String SQL = "INSERT INTO amb.lieky(nazov, kod, vyrobca, predajca, cena_poistovna, cena_pacient) " + "VALUES('Paralen, '12459', 'Zentiva', 'Dr. Max', 1.5, 2.4)";
```

Ukážka grafického rozhrania

![Pridanie lieku](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridanieLieku.png?token=AetJR-mWou9hlBFlDvMhTCLnyDOKDDvIks5csvpYwA%3D%3D "Pridanie lieku")

Ukážka grafického rozhrania pre úspešne pridanie

![Dialg pridanie lieku](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/InformationDialog.png?token=AetJR3IiQrsizl23Q6sobC0a1MnLhZizks5csv1wwA%3D%3D "Dialog pridanie lieku")

### Správa očkovaní </h3>
#### Pridanie očkovania</h4>

Pri pridávani nového očkovania do databázy musí používaľ zadať všetky položky. Ako v predchazajúch prípadov tak aj tu sú ošetrene uloženia iba validných údajov do databázy. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu. 

```mySQL
String SQL = "INSERT INTO amb.zoznam_ockov(nazov, popis_ockovania) " + "VALUES('Tetanus','Tetanus je nebezpečné bakteriální infekční onemocnění. Spóry bakterie Clostridium tetani jsou celosvětově rozšířeny v půdě.')";
```

Ukážka grafického rozhrania

![Pridanie ockovania](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridanieOckovania.png?token=AetJR4n9E2jCcyW4kxF9bIgizF3f8WJkks5csvp-wA%3D%3D "Pridanie ockovania")

#### Zobrazenie očkovania</h4>

Pri zobrazení očkovaní sa používateľovi ukáže, aké očkovania daný pacient už podstupil.

```mySQL
String SQL = SELECT z.nazov, z.popis_ockovania, o.popis FROM amb.ockovania as o
	inner join amb.zoznam_ockov as z ON z.id_zoznam_ockov = o.id_zoznam_ockov
WHERE o.id_pacient = 8 ";
```

Ukážka grafického rozhrania

![Zobrazenie ockovania](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/zobrazZoznamOckovani.png?token=AetJRwqfd6bRvMFg4BjjTVecg4IhT3c8ks5csvsGwA%3D%3D "Zobrazenie ockovania")

### Správa návštev </h3>
#### Pridanie a zobrazenie návštevy</h4>

Pri pridávaní novej návštevy do databázy, si pužívateľ zadá diagnózu pacienta a taktiež mu predpíše liek. 

Ukážka grafického rozhrania

![Pridanie navstevy](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridanieNavstevy.png?token=AetJR6yw6aBeApm4qcChklKlaMWtKTffks5csvvIwA%3D%3D "Pridanie navstevy")

#### Vyhľadanienie návštevy</h4>

Na tomto scenáry sa aktuálne pracuje.


### Správa chorôb</h3> 
#### Pridanie diagnózy a vyhľadanie choroby</h4>

Pri pridávaní novej diagnózy do databázy musí používateľ zadať popis diagnózy. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu. 

```mySQL
String SQL = "INSERT INTO amb.diagnozy(id_choroby, popis) VALUES(555, 'Bolest Brucha')";
```

Vyhľadanie choroby nám zobrazí požadovanú chorobu a jej popis.

```mySQL
String SQL = "SELECT * FROM amb.choroby WHERE odborny_nazov LIKE 'Pal%' LIMIT 50;";
```

Ukážka grafického rozhrania

![Pridanie diagnozy a vyhladanie choroby](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridanieDiagnozy.png?token=AetJRxerMadCYibZx1xW1HhbvUTfYaKqks5csvtJwA%3D%3D "Pridanie diagnozy a vyhladanie choroby")

#### Pridanie choroby </h4>

Pri pridávaní novej diagnózy do databázy musí používateľ zadať názov, kod a popis choroby. Pri naplňani ukladáme údaje cez parametrové vstupy aby sa predišlo útokom na databázu. 

```mySQL
String SQL = "INSERT INTO amb.choroby(odborny_nazov, kod_choroby, popis) VALUES ('Nadch', '85266', 'Bolest hlavy, upchaty nos, slzenie oci')";
```
Ukážka grafického rozhrania

![Pridanie choroby](https://raw.githubusercontent.com/fiit-dbs-2019/dbs2019-project-assignment-jakub-simon/2.-kontrolny-bod/schema/pridanieChoroby.png?token=AetJR0hZnuLV0a9TennPCEo3oar3dO7Mks5csv9cwA%3D%3D "Pridanie choroby")


