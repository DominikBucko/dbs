# Asset manager
## Zadanie</H1>
Tento projekt slúži na evidenciu assetov (softvérov, hardvérov). Patrí k nemu aj databáza, ktorá je ulozená na servery dbs.fiit.uk.to servery (dbs.fiit.uk.to) a 
obsahuje tabulky rôzne tabulky pre správne ulozenie jednotlivých poloziek.

## Špecifikácia scenárov</H2>
### Správa použivateľov
Admin pri pridávaní použivateľa do databázy zadáva meno, priezvisko, mesto ,psč, ulicu. Taktiež musí mu vygenerovať meno a heslo. 
Po vykonaní tejto akcie sa v databáze vytvorí záznam o novom použivatelovi a zobrazí sa dialógové okno s informáciou o úspešnom pridaní použivateľa.

Modifikáciu informácií o použivatelovy je realizované zadáním iba zmenenéných údajov adminom. Po vykonaní zmeny sa údaje prepíšu. 

Pre vyhľadanie, postačí, ak sa zadá jeden z atribútov použivateľa.

Admin pre zmazanie použivateĺa musí označiť vyhľadaného pacienta stlačiť tlačidlo zmazať, nasledne potvrdiť voľbu.
Použivateľ nesmie mať ziadne vypožičané assety aby sa z databázy odstránil záznam o použivateľovi, ak to tak není zobrazí sa dialogove okno o oznameni 
neodstraneni použivateľa z dôsledku aktivných vypožičaní, ak použivateľ nema aktivne vypožičania tak sa zobrazí okno s informáciou o uspešnom zmazaní pacienta.

Zobrazenie použivateľa je vyriešene rozkliknutím vyhľadaného pacienta. Po rozkliknutí sa otvorí okno s informáciami o použivatelovia a zoznamom vypožičaných assetov.

### Vyradenie assetu
Admin pre vyradenie assetu z databázy si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo zmazať a potvrdí voľbu.
Asset musí byť voľný, ak je tak sa zobrazí okno o potvrdení odstránenia assetu, inak sa zobrazí okno o nezmazaní assetu z databazy z dôsledku aktivného vypožičania.

### Presun assetu na ine oddelenie
Admin pre presun assetu na ine oddelenie si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo presunuť asset. 
Zobrazí sa mu okno kde vyberie oddelenie kam chce asset presunúť a klikne na tlačidlo presunuť. Asset musí byť voľný, ak je tak  sa zobrazí okno o potvrdení presunutia assetu na oddelenie,
inak sa zobrazí okno o nepridaní assetu na oddelenie z dôsledku aktivného vypožičania.

 
### Vydanie assetu na servis
Admin pre zaradenie assetu do servisu si vyhľadá asset pomocou niektorých z atribútov, následne si ho označi, klikne na tlačidlo odoslať na servis. 
Vyplní komentár k chybe a klikne na tlačidlo odoslať. Asset nemože byť vypožičaný, ak je tak sa zobrazí okno o nepridaní assetu na servis z dôsledku aktivného vypožičania, inak sa zobrazí okno o potvrdení pridania assetu na servis.


### Pridanie assetu
Admin pri pridávaní assetu do databázy zadáva názov, typ, vyberie kategóriu a oddelenie a vygeneruje QR kód, stav sa automatický nastaví na voľný.
Po vykonaní tejto akcie sa v databáze vytvorí zaznam o novom assete a zobrazí sa dialógové okno s informáciaciou o úspešnom pridaní assetu.

### Požiadavka o pridelenie assetu
Použivateľ stlačí tlačidlo pre požiadanie o asset, kde si vyberie, ktorý asset potrebuje, nasledne ho označí a klikne na tlačidlo požiadať. 
Potom môže ale nemusí vyplniť komentár a stači tlačidlo požiadať. Požiadavka sa pridá do tabuľky ticket a použivateľovi sa zobrazí okno o úspešnom registrovaní požiadavky.

### Schválenie požiadavky pre pridelenie a vydanie assetu
Admin označí požiadavku ktorú chce potvrdiť a nasledne stlačí tlačidlo schváliť, zobrazí sa mu okno o overení schvalenia, ktorú ak potvrdí, zobrazí sa mu okno o úspešnom schválení požiadavky.

Použivateľ po úspešnom schvalení si môže vyzdvihnuť asset, kontaktuje admina a ten si vyhľadá požiadavku a odklikne tlačidlo pre odovzdanie assetu. Zobrazí sa mu okno o úspešnom zaregistrovaní odovzdania assetu a do tabuľky je pridaný dátum odovzdania. 


## Logický model</H3>
[Logicky model](fyzicky_model.png "Logicky model")
obsahuje sedem tabuliek. Sú v ňom pridané kardinality medzi jednotlivými tabuľkami.

## Fyzický model</H4>
[Fyzicky model](logicky_model.jpg "Fyzicky model") 
obsahuje desať tabuliek. Každá tabuľka má primárny klúč
