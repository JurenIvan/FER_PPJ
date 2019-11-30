from Gramatika import *
from funkcije import *

# filename = "test.txt"
filename = "gramatikaPrimjer.txt"
with open(filename) as f:
    gramatika = Gramatika(f.readlines())

znakovi = []
for x in gramatika.nezavrsni_znakovi:
    if (x != gramatika.pocetni_nezavrsni_znak):
        znakovi.append(x)
for x in gramatika.zavrsni_znakovi:
    if x != "$":
        znakovi.append(x)

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
    print(nedovrseno_stanje)

for x in DKA:
    print("Stanje ", x[0])
    for ntorka in x[1]:
        print(ntorka)
    print()

print("PRIJELAZI AUTOMATA")
for k, v in prijelazi_automata.items():
    print("{} -> {}".format(k, v))

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
