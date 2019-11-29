fajl = open("gramatikaPrimjer.txt")

citaj = fajl.readlines()

pocetniNezavrsniZnak = ""
tmp = citaj[0].split()

nezavrsniZnakovi = tmp[1:]
pocetniNezavrsniZnak = nezavrsniZnakovi[0]

tmp = citaj[1].split()

zavrsniZnakovi = set()
zavrsniZnakovi.add("$")
zavrsniZnakovi.update(tmp[1:])

sinkronizacijskiZnakovi = []
tmp = citaj[2].split()
sinkronizacijskiZnakovi = tmp[1:]

# mozda ce trebait preimenovati NoviPocetniNezvarsniZnak
NNZ = "<NoviNezavrsniZnak>"
# gramatika = {}
stanje_na_produkcije = {}
# produkcije = []
produkcije_tuple = []
trenutniKljuc = ""
citaj.insert(3, NNZ)
citaj.insert(4, " " + pocetniNezavrsniZnak)
for x in citaj[3:]:
    if (x[0] == "<"):
        trenutniKljuc = x.strip("\n")
        # if (trenutniKljuc not in gramatika.keys()):
        #     gramatika.update({trenutniKljuc: []})
    else:
        # gramatika[trenutniKljuc].append(x.split())
        lijevaStrana = trenutniKljuc
        desnaStrana = ""
        tmp = x.split()
        cnt = 0
        for k in tmp:
            desnaStrana += k
            if (cnt < len(tmp) - 1):
                desnaStrana += ", "
            cnt += 1

        # produkcije.append(lijevaStrana + " -> " + desnaStrana)
        produkcije_tuple.append((lijevaStrana, x.split()))

        if (lijevaStrana not in stanje_na_produkcije.keys()):
            stanje_na_produkcije[lijevaStrana] = []
        stanje_na_produkcije[lijevaStrana].append(len(produkcije_tuple) - 1)

print(produkcije_tuple)
print(stanje_na_produkcije)

# gramatika[NNZ] = [[pocetniNezavrsniZnak]]

brojacStanja = 0
stanje = []
unikati = set()

pocetnoStanje = (0, 0, "@")
stanje.append(pocetnoStanje)
unikati.add(pocetnoStanje)

for x in stanje:
    produkcija = produkcije_tuple[x[0]]

    if (x[2] == len(produkcija[1])):
        # TODO redukcija je ovo
        continue

    znak_iza_tocke = produkcija[1][x[1]]
    if znak_iza_tocke in zavrsniZnakovi:
        continue

    dohvati_produkcije = stanje_na_produkcije[znak_iza_tocke]
    for k in dohvati_produkcije:
        ntorka = (k, 0, "@@@")
        if (ntorka not in unikati):
            unikati.add(ntorka)
            stanje.append(ntorka)

print(stanje)
print(unikati)


def odredi_kontekst(ntorka):
    # TODO
    pass


def kontekst(alpha, parent_context):
    # TODO
    # ime bolje..
    pass


# alpha je niz od nezavrsnih/zavrsnih znakova
def prazan_skup(alpha):
    if len(alpha) == 0:
        return True

    prvi = alpha[0]

    if(zavrsniZnakovi):
        pass

    # TODO
    # ako se alpha moze pretvoriti u $
    # returna true false
    pass


def zapocinje(alpha):
    # TODO
    # returna npr "a b @"
    pass


# ispis
out = open("out", "w")

for x in nezavrsniZnakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in zavrsniZnakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in sinkronizacijskiZnakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
# for x in produkcije:
#     out.write(x + "\n")

if __name__ == '__main__':
    pass
