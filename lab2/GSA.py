from funkcije import *


fajl = open("test.txt")

citaj = fajl.readlines()

pocetni_nezavrsni_znak = ""
tmp = citaj[0].split()

nezavrsni_znakovi = tmp[1:]
pocetni_nezavrsni_znak = nezavrsni_znakovi[0]

tmp = citaj[1].split()

zavrsni_znakovi = set()
zavrsni_znakovi.add("$")
zavrsni_znakovi.update(tmp[1:])

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
citaj.insert(4, " " + pocetni_nezavrsni_znak)
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

#print(produkcije_tuple)
#print(stanje_na_produkcije)

# gramatika[NNZ] = [[pocetni_nezavrsni_znak]]

brojac_stanja = 0
DKA = []
stanje = []
unikati = set()


pocetno_stanje = (0, 0, "@")
stanje.append(pocetno_stanje)
unikati.add(pocetno_stanje)

znakovi = []
for x in nezavrsni_znakovi:
    znakovi.append(x)
for x in zavrsni_znakovi:
    if (x != "$"):
        znakovi.append(x)

#print("znakovi su: ", end = "")
#print(znakovi)

#print(produkcije_tuple)
#print(stanje)
kreirano_je_novo_stanje = True

neobradene_produkcije = [pocetno_stanje]

while (neobradene_produkcije != []):

    # ubaci u stanje sve epsilon prijelaze
    for x in stanje:
        produkcija = produkcije_tuple[x[0]]
        #print("produkcije je {}".format(produkcija))
        if (x[2] == len(produkcija[1])):
            # TODO redukcija je ovo
            continue

        znak_iza_tocke = produkcija[1][x[1]]
        if znak_iza_tocke in zavrsni_znakovi:
            continue

        dohvati_produkcije = stanje_na_produkcije[znak_iza_tocke]
        for k in dohvati_produkcije:
            ntorka = (k, 0, "@@@")
            if (ntorka not in unikati):
                unikati.add(ntorka)
                stanje.append(ntorka)
                neobradene_produkcije.append(ntorka)

        stanje = sorted(stanje, key=lambda tup: (tup[0]))
        #print(stanje)
        #print(unikati)
    del(neobradene_produkcije[0])
        # prije DKA append ide provjera jel to stanje vec postoji 
        # >> da prodes po tuple[1] i usporedis sa vec postojecima u DKA
if (postoji_stanje_u_DKA(stanje, DKA) == False):
    DKA.append((brojac_stanja, stanje))
    brojac_stanja += 1
    kreirano_je_novo_stanje = True
    ### do ovdje je kreirano stanje

for znak in zavrsni_znakovi - {"$"}:
    for x in stanje:
        #print("x je {}".format(x))
        br_produkcije = x[0]
        indeks_tocke = x[1]

        produkcija_za_prijelaz = produkcije_tuple[br_produkcije]
        iduci_znak_u_produkciji = produkcija_za_prijelaz[1][indeks_tocke]
        #print("{} {}".format(produkcija_za_prijelaz, iduci_znak_u_produkciji))

        novo_stanje = []
        if (iduci_znak_u_produkciji == znak):
            #print("jeste: {}".format(iduci_znak_u_produkciji))
            #print("iduci znak: {}".format(iduci_znak_u_produkciji))
            novo_stanje.append((br_produkcije, indeks_tocke + 1, "@@@"))
    
    if (postoji_stanje_u_DKA(novo_stanje, DKA) == False):
        #print("NOVO STANJE!")
        DKA.append((brojac_stanja, novo_stanje))
        brojac_stanja += 1
        kreirano_je_novo_stanje = True




for x in DKA:
    print(x)

print(produkcije_tuple)


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

    if(zavrsni_znakovi):
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

for x in nezavrsni_znakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in zavrsni_znakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in sinkronizacijskiZnakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
# for x in produkcije:
#     out.write(x + "\n")

if __name__ == '__main__':
    pass
