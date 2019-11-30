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
pocetno_stanje = (0, 0, "@")
stanje.append(pocetno_stanje)

# exit()

stvori_stanje(gramatika, pocetno_stanje, gramatika.produkcije, gramatika.zavrsni_znakovi, gramatika.znak_na_produkcije, stanje)

#print(stanje)
# exit()

# s iducom naredom zavrsava kreiranje stanja
dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja)
brojac_stanja += 1

#print(radi_novo_stanje)
#print(gramatika.znak_na_produkcije)
#print(gramatika.produkcije)

prijelazi_automata = {}
for x in znakovi:
    #a = 5
    #print(x)
    novo_stanje = []
    stanje_za_obraditi = DKA[0][1]
    #print(stanje_za_obraditi)
    for k in stanje_za_obraditi:
        #print("{} {}".format(k[0], k[1]))
        #print(k)
        produkcija = gramatika.produkcije[k[0]]
        na_mjestu_tocke = produkcija[1][k[1]]
        #print(indeks_tocke)
        if (na_mjestu_tocke == x):
            #print(x)
            #print("k je: {}".format(k))
            #print("jeste")
            #print(na_mjestu_tocke)
            novo_stanje.append((k[0], k[1] + 1, k[2]))
            
    # tu treba ici provjera postoji li vec to stanje ili je novo stanje
    # pa s obzirom na to napraviti usmjeravanje u dictionaryju prijelaza stanja
    if (dodaj_stanje_u_DKA(novo_stanje, DKA, brojac_stanja) == True):
        prijelazi_automata[(nedovrseno_stanje[0], x)] = brojac_stanja
        nedovrseno_stanje.append(brojac_stanja)
        brojac_stanja += 1
    else:
        for k in DKA:
            if (novo_stanje == k[1]):
                prijelazi_automata[(nedovrseno_stanje[0], x)] = k[0]
                break

print(nedovrseno_stanje)

for x in DKA:
    print(x)

# print("PRIJELAZI AUTOMATA!")
# for k, v in prijelazi_automata.items():
#     #print(x)
#     print("{} -> {}".format(k, v))

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

# if __name__ == '__main__':
#     with open("out", "r") as out:
#         print("\n\n")
#         for line in out.readlines():
#             print(line, end="")
