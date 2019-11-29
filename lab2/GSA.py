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
dovrseno_stanje = []
nedovrseno_stanje = []
stanje = []

nedovrseno_stanje = [0]
pocetno_stanje = (0, 0, "@")
stanje.append(pocetno_stanje)

# exit()

stvori_stanje(pocetno_stanje, gramatika.produkcije, gramatika.zavrsni_znakovi, gramatika.znak_na_produkcije, stanje)

print(stanje)
# exit()

# s iducom naredom zavrsava kreiranje stanja
dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja)

for znak in znakovi:
    print(znak, end=" ")

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
