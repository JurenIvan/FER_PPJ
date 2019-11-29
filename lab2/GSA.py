from Gramatika import *
from funkcije import *

filename = "test.txt"
# filename = "gramatikaPrimjer.txt"
with open(filename) as f:
    gramatika = Gramatika(f.readlines())

brojac_stanja = 0
DKA = []
stanje = []
unikati = set()

pocetno_stanje = (0, 0, "@")
stanje.append(pocetno_stanje)
unikati.add(pocetno_stanje)

znakovi = []
for x in gramatika.nezavrsni_znakovi:
    znakovi.append(x)
for x in gramatika.zavrsni_znakovi:
    if x != "$":
        znakovi.append(x)

kreirano_je_novo_stanje = True
neobradene_produkcije = [pocetno_stanje]

while neobradene_produkcije != []:

    # ubaci u stanje sve epsilon prijelaze
    for x in stanje:
        produkcija = gramatika.produkcije[x[0]]
        # print("produkcije je {}".format(produkcija))
        if x[2] == len(produkcija[1]):
            # TODO redukcija je ovo
            continue

        znak_iza_tocke = produkcija[1][x[1]]
        if znak_iza_tocke in gramatika.zavrsni_znakovi:
            continue

        dohvati_produkcije = gramatika.znak_na_produkcije[znak_iza_tocke]
        for k in dohvati_produkcije:
            ntorka = (k, 0, "@@@")
            if ntorka not in unikati:
                unikati.add(ntorka)
                stanje.append(ntorka)
                neobradene_produkcije.append(ntorka)

        stanje = sorted(stanje, key=lambda tup: (tup[0]))
        # print(stanje)
        # print(unikati)
    del (neobradene_produkcije[0])
    # prije DKA append ide provjera jel to stanje vec postoji
    # >> da prodes po tuple[1] i usporedis sa vec postojecima u DKA
if not postoji_stanje_u_DKA(stanje, DKA):
    DKA.append((brojac_stanja, stanje))
    brojac_stanja += 1
    kreirano_je_novo_stanje = True
    ### do ovdje je kreirano stanje

for znak in gramatika.zavrsni_znakovi - {"$"}:
    for x in stanje:
        # print("x je {}".format(x))
        br_produkcije = x[0]
        indeks_tocke = x[1]

        produkcija_za_prijelaz = gramatika.produkcije[br_produkcije]
        iduci_znak_u_produkciji = produkcija_za_prijelaz[1][indeks_tocke]
        # print("{} {}".format(produkcija_za_prijelaz, iduci_znak_u_produkciji))

        novo_stanje = []
        if iduci_znak_u_produkciji == znak:
            # print("jeste: {}".format(iduci_znak_u_produkciji))
            # print("iduci znak: {}".format(iduci_znak_u_produkciji))
            novo_stanje.append((br_produkcije, indeks_tocke + 1, "@@@"))

    if not postoji_stanje_u_DKA(novo_stanje, DKA): # TODO moze li novo_stanje biti nedefinirano?
        # print("NOVO STANJE!")
        DKA.append((brojac_stanja, novo_stanje))
        brojac_stanja += 1
        kreirano_je_novo_stanje = True

for x in DKA:
    print(x)

# ispis u datoteku "out"
with open("out", "w") as out:
    for x in gramatika.nezavrsni_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    for x in gramatika.zavrsni_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    for x in gramatika.sinkronizacijski_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    for x in gramatika.produkcije:
        out.write(x[0] + " -> " + ", ".join(x[1]) + "\n")
    out.close()

if __name__ == '__main__':
    with open("out", "r") as out:
        print("\n\n")
        for line in out.readlines():
            print(line, end="")
