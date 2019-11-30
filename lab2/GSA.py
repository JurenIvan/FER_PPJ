from Gramatika import *
from funkcije import *

filename = "test.txt"
# filename = "gramatikaPrimjer.txt"
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
    #print(nedovrseno_stanje)

# for x in DKA:
#     print("Stanje ", x[0])
#     for ntorka in x[1]:
#         print(ntorka)
#     print()

# print("PRIJELAZI AUTOMATA")
# for k, v in prijelazi_automata.items():
#     print("{} -> {}".format(k, v))

tablica = []
znakovi.append("@")
znakovi.append(gramatika.pocetni_nezavrsni_znak)
# print(znakovi)

# konacno_stanje_automata = prijelazi_automata[(0, gramatika.pocetni_nezavrsni_znak)]

# for x in DKA:
#     dohvati_stanje = x[0]

#     for znak in znakovi:
#         if ((dohvati_stanje, znak) in prijelazi_automata.keys()):
#             if (znak in gramatika.zavrsni_znakovi):
#                 dohvati_prijelaz = prijelazi_automata[(dohvati_stanje, znak)]
#                 tablica.append(str(dohvati_stanje) + ", " + znak + ", POMAKNI, " + str(dohvati_prijelaz))    
#             elif (znak in gramatika.nezavrsni_znakovi):
#                 dohvati_prijelaz = prijelazi_automata[(dohvati_stanje, znak)]
#                 tablica.append(str(dohvati_stanje) + ", " + znak + ", STAVI, " + str(dohvati_prijelaz))
                
#         elif (dohvati_stanje == konacno_stanje_automata and znak == "@"):
#             tablica.append(str(dohvati_stanje) + ", " + znak + ", PRIHVATI")
#         else:
#             # provjeri ima li mozda redukcija za taj znak, 
#             #   tj. je li koja produkcija u stanju na kraju i da ima znak u kontekstu
#             # ako nema, samo odbaci NIZ
#             # print("jeste", znak, dohvati_stanje)
#             produkcije_stanja = DKA[dohvati_stanje][1]
#             mogu_reducirati = False
#             print(produkcije_stanja)
#             print(znak)
            
#             if (znak in gramatika.nezavrsni_znakovi or znak == "@"):
#                 #print("ETO ME")
#                 for k in produkcije_stanja:
#                     # je li na kraju produkcija
#                     pozicija_tocke = k[1]
#                     kontekst = k[2].split()
#                     print("produkcija jeste: ", gramatika.produkcije[k[0]][1])
#                     print("len jeste: ", len(gramatika.produkcije[k[0]][1]))
#                     #exit()
#                     if (pozicija_tocke == len(gramatika.produkcije[k[0]]) - 1):
#                         if (znak in kontekst):
#                             #print("JESTE")
#                             tablica.append(str(dohvati_stanje) + ", " + znak + ", REDUCIRAJ, " + str(k[0]))
#                             mogu_reducirati = True
#                             break
#             if (mogu_reducirati == False):
#                 tablica.append(str(dohvati_stanje) + ", " + znak + ", ODBACI")
#             #exit()


for x in tablica:
    print(x)

#print(znakovi)
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
            out.write("{}, {}, {}, {}\n".format(k[0], k[1], "STAVI", v))
            iskoristeni_elementi.append((k[0], k[1]))
        else:
            out.write("{}, {}, {}, {}\n".format(k[0], k[1], "POMAKNI", v))
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
    #print(iskoristeni_elementi)
    for x in DKA:
        for y in znakovi:
            if ((x[0], y) not in iskoristeni_elementi):
                out.write("{}, {}, {}\n".format(x[0], y, "ODBACI"))

    out.write("#" * 10 + "\n")
    for x in gramatika.produkcije:
        out.write(x[0] + " -> " + ", ".join(x[1]) + "\n")
    out.close()

# if __name__ == '__main__':
#     with open("data.txt", "r") as out:
#         print("\n\n")
#         for line in out.readlines():
#             print(line, end="")
