from Gramatika import *
from funkcije import *
import sys
#filename = "tests/kanon_gramatika.san"
# filename = "test.txt"
# filename = "gramatikaPrimjer.txt"
inputLines = sys.stdin.read().splitlines()
gramatika = Gramatika(inputLines)

brojac_stanja = 0
DKA = []
nedovrseno_stanje = []
stanje = []

nedovrseno_stanje = [0]
pocetne_ntorke = [(0, 0, "@")]
stanje.append(pocetne_ntorke)

stanje = stvori_stanje(gramatika, pocetne_ntorke, gramatika.produkcije, gramatika.zavrsni_znakovi,
                       gramatika.znak_na_produkcije)
dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja)
brojac_stanja += 1

prijelazi_automata = {}
while (nedovrseno_stanje != []):

    nedovrseno_stanje_indeks = nedovrseno_stanje[0]
    assert (nedovrseno_stanje_indeks == DKA[nedovrseno_stanje_indeks][
        0])  # TODO ako dobro shvacam povezanost brojaca i stanja

    nedovrseno_stanje_znakovi_prijelaza = {}
    stanje_za_obraditi = DKA[nedovrseno_stanje_indeks][1]

    for ntorka in stanje_za_obraditi:

        ntorka_tocka = ntorka[1]
        ntorka_produkcija = gramatika.produkcije[ntorka[0]]
        ntorka_produkcija_desna_strana = ntorka_produkcija[1]
        if ntorka_produkcija_desna_strana[0] == "$" or len(ntorka_produkcija_desna_strana) <= ntorka_tocka:
            continue

        ntorka_znak_iza_tocke = ntorka_produkcija_desna_strana[ntorka_tocka]
        if ntorka_znak_iza_tocke not in nedovrseno_stanje_znakovi_prijelaza:
            nedovrseno_stanje_znakovi_prijelaza[ntorka_znak_iza_tocke] = []

        nedovrseno_stanje_znakovi_prijelaza[ntorka_znak_iza_tocke].append((ntorka[0], ntorka[1] + 1, ntorka[2]))

    for znak, pocetne_ntorke in nedovrseno_stanje_znakovi_prijelaza.items():
        stanje = stvori_stanje(gramatika, pocetne_ntorke, gramatika.produkcije, gramatika.zavrsni_znakovi,
                               gramatika.znak_na_produkcije)

        indeks_stanja = dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja)
        if indeks_stanja == brojac_stanja:
            nedovrseno_stanje.append(brojac_stanja)
            brojac_stanja += 1

        prijelazi_automata[(nedovrseno_stanje_indeks, znak)] = indeks_stanja

    del (nedovrseno_stanje[0])


znakovi = []
for x in gramatika.nezavrsni_znakovi:
    if (x != gramatika.stari_pocetni_nezavrsni_znak):
        znakovi.append(x)
for x in gramatika.zavrsni_znakovi:
    if x != "$":
        znakovi.append(x)
znakovi.append("@")
znakovi.append(gramatika.novi_pocetni_nezavrsni_znak)

# ispis u datoteku "data.txt"
with open("data.txt", "w") as out:
    for x in gramatika.nezavrsni_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    for x in gramatika.zavrsni_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    for x in gramatika.sinkronizacijski_znakovi:
        out.write(x + "\n")
    out.write("#" * 10 + "\n")
    
    iskoristeni_elementi = []
    for k, v in prijelazi_automata.items():
        if k[1] in gramatika.zavrsni_znakovi:
            out.write("{}, {}, {}, {}\n".format(k[0], k[1], "POMAKNI", v))
            iskoristeni_elementi.append((k[0], k[1]))
        else:
            out.write("{}, {}, {}, {}\n".format(k[0], k[1], "STAVI", v))
            iskoristeni_elementi.append((k[0], k[1]))
    for x in DKA:
        for ntorka in x[1]:
            produkcija = gramatika.produkcije[ntorka[0]]
            if produkcija[1][0] == "$" or len(produkcija[1]) == ntorka[1]:
                for znak in ntorka[2].split():
                    if ntorka[0] == 0:
                        out.write("{}, {}, {}\n".format(x[0], znak, "PRIHVATI"))
                        iskoristeni_elementi.append((x[0], znak))
                    else:
                        out.write("{}, {}, {}, {}\n".format(x[0], znak, "REDUCIRAJ", ntorka[0]))
                        iskoristeni_elementi.append((x[0], znak))

    for x in DKA:
        for y in znakovi:
            if ((x[0], y) not in iskoristeni_elementi):
                out.write("{}, {}, {}\n".format(x[0], y, "ODBACI"))

    out.write("#" * 10 + "\n")
    for x in gramatika.produkcije:
        out.write(x[0] + " -> " + ", ".join(x[1]) + "\n")
    out.close()

