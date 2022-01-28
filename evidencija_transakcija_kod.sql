create database evidencija_transakcija;

set sql_safe_updates = 0;

use evidencija_transakcija;

--drop table Valuta;
create table if not exists Valuta(
	kod_valute varchar(3) primary key, constraint check_kod_V check(char_length(kod_valute) = 3),
    naziv_valute varchar(30) not null,
    kurs real not null  -- курс у односу на динар!
);

--drop table Vlasnik;
create table if not exists Vlasnik(
	redni_br int auto_increment primary key,
    ime varchar(30) not null,
    prezime varchar(30) not null,
    e_mail varchar(50) not null,
    adresa varchar(100) not null,
    br_telefona varchar(30) not null
);

--drop table Racun;
create table if not exists Racun(
	br_racuna varchar(30) primary key,
    naziv_banke varchar(100) not null,
    redni_br int not null,
    foreign key(redni_br) references Vlasnik(redni_br) on update restrict on delete restrict
);

--drop table Transakcija;
create table if not exists Transakcija(
	red_br int auto_increment primary key,
    tip enum('uplata', 'isplata') not null,
    iznos real not null, constraint check_iznos check(iznos > 0),
    kod_valute varchar(3) not null, constraint check_kod_T check(char_length(kod_valute) = 3),
    datum datetime not null default CURRENT_TIMESTAMP,
    svrha varchar(100) default 'Svrha nije unesena',
    br_racuna varchar(30) not null,
    foreign key(kod_valute) references Valuta(kod_valute) on update cascade on delete restrict,
    foreign key(br_racuna) references Racun(br_racuna) on update restrict on delete restrict
);

-- Унос података за валуте:
insert into Valuta values('RSD', 'Srpski dinar', 1.0);
insert into Valuta values('USD', 'Americki dolar', 96.18);
insert into Valuta values('EUR', 'Evro', 117.21);
insert into Valuta values('BAM', 'Konvertibilna marka', 59.93);
insert into Valuta values('GBP', 'Britanska funta', 136.39);
insert into Valuta values('AUD', 'Australijski dolar', 74.56);
insert into Valuta values('RUB', 'Rublja', 1.34);
insert into Valuta values('SEK', 'Svedska kruna', 11.7);
insert into Valuta values('CHF', 'Svajcarski franak', 107.58);
insert into Valuta values('CAD', 'Kanadski dolar', 79.55);
insert into Valuta values('HRK', 'Hrvatska kuna', 15.62);

-- Унос података за власнике рачуна:
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona) 
	--values('Dejan', 'Vukomanovic', 'dejanv@gmail.com', 'Kneza Milosa 23a, Beograd, Srbija',  '061111222');
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona)
	--values('Milos', 'Stamenkovic', 'milosstam@gmail.com', 'Kraljevska 24, Kraljevo, Srbija',  '061111333');
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona)
	--values('Janko', 'Bratic', 'jank0@gmail.com', 'Beogradska 1, Banja Luka, Republika Srpska BiH',  '066112444');
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona)
	--values('Branko', 'Savovic', 'bran_savov@gmail.com', 'Kneza Milana bb, Subotica, Srbija',  '061111555');
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona)
	--values('Slavko', 'Nedeljkovic', 'slavkonedeljko@gmail.com', 'Ive Andrica 2a, Beograd, Srbija',  '061111666');
--insert into Vlasnik(ime, prezime, e_mail, adresa, br_telefona)
	--values('Nemanja', 'Draskovic', 'nemanjaaa@gmail.com', 'Svetog Save 11, Nis, Srbija',  '061111777');

-- Унос података за рачуне:
--insert into Racun(br_racuna, naziv_banke, redni_br) values('5555333322227777', 'Komercijalna banka', 3);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('2222111155557778', 'UniCredit banka', 1);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('4444222266661111', 'Raifeissen banka', 5);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('3333555577770000', 'Vojvodjanska banka', 3);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('5555777700001122', 'Komercijalna banka', 2);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('6666222211114454', 'Raiffeisen banka', 6);
--insert into Racun(br_racuna, naziv_banke, redni_br) values('7777333355551234', 'Agro banka', 4);


-- Унос података за трансакције:
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('uplata', 155.00, 'prodaja telefona', 'EUR', '5555333322227777');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('uplata', 200.00, 'bonus na platu', 'USD', '2222111155557778');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('isplata', 300.00, 'kupovina ves masine', 'EUR', '4444222266661111');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('uplata', 3000.00, 'osiguranje', 'RSD', '3333555577770000');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('uplata', 400.00, 'prodaja laptopa', 'EUR', '5555777700001122');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('isplata', 5000.00, 'kupovina felne', 'RSD', '6666222211114454');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('isplata', 250.00, 'zamjena prozora', 'EUR', '7777333355551234');
--insert into Transakcija(tip, iznos, svrha, kod_valute, br_racuna) 
	--values('uplata', 300.00, 'bonus na platu', 'USD', '2222111155557778');


---- Приказ свих расположивих валута:
--select * from Valuta;

---- Приказ свих власника рачуна (оних који учествују у трансакцијама)
--select * from Vlasnik;

---- Приказ свих рачуна и података о њима:
--select * from Racun;

---- Приказ свих обављених трансакција:
--select * from Transakcija;

---- Приказ суме свих уплаћених износа (у динарима):
--select sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)) 
		--from Transakcija where tip='uplata';

---- Приказ суме свих исплаћених износа (у динарима):
--select sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)) 
		--from Transakcija where tip='isplata';
        
---- Приказ тренутног стања на рачуну тј. 
---- (разлика суме уплаћених и исплаћених износа - може бити негативна вриједност)
--select(
	--ifNull((select sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)),0) 
		--from Transakcija where tip='uplata')- 
	--ifNull((select sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)),0) 
		--from Transakcija where tip='isplata')); 

---- Приказ података о власницима и рачунима које посједују
--select ime, prezime, r.redni_br, br_racuna, naziv_banke, e_mail, adresa, br_telefona 
	--from Racun r, Vlasnik v where r.redni_br=v.redni_br; 

---- Приказ имена и презимена учесника, бројева рачуна, 
---- типа трансакције, износа, валуте, датума за сваку трансакцију:
--select ime, prezime, t.br_racuna, br_telefona, tip, iznos, kod_valute, datum 
	--from Racun r, Vlasnik v, Transakcija t 
		--where r.redni_br=v.redni_br and r.br_racuna=t.br_racuna; 

---- Приказ укупног броја уплата и укупног броја исплата:  
--select tip, count(*) from Transakcija group by tip;

---- Приказ свих трансакција које су извршене прије 2021-12-13 22:37:54:
--select * from Transakcija where datum<'2021-12-13 22:37:54';

---- Приказ имена и презимена, бројева рачуна, 
---- типа трансакције, износа, валуте, датума за сваку трансакцију
---- у којој је учествовао Милош Стаменковић:
--select ime, prezime, t.br_racuna, br_telefona, tip, iznos, kod_valute, datum 
	--from Racun r, Vlasnik v, Transakcija t 
		--where r.redni_br=v.redni_br and r.br_racuna=t.br_racuna
			--and v.ime='Milos' and v.prezime='Stamenkovic'; 
 
