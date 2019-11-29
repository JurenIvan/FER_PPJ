from funkcije import *

filename = "test.txt"
# filename = "gramatikaPrimjer.txt"
fajl = open(filename)

citaj = fajl.readlines()

pocetni_nezavrsni_znak = ""
tmp = citaj[0].split()

nezavrsni_znakovi = tmp[1:]
pocetni_nezavrsni_znak = nezavrsni_znakovi[0]

tmp = citaj[1].split()

zavrsni_znakovi = set()
zavrsni_znakovi.add("$")
zavrsni_znakovi.update(tmp[1:])

sinkronizacijski_znakovi = []
tmp = citaj[2].split()
sinkronizacijski_znakovi = tmp[1:]

# mozda ce trebait preimenovati NoviPocetniNezvarsniZnak
NNZ = "<NoviNezavrsniZnak>"
# gramatika = {}
stanje_na_produkcije = {}
# produkcije = []
produkcije_tuple = []
trenutni_kljuc = ""
citaj.insert(3, NNZ)
citaj.insert(4, " " + pocetni_nezavrsni_znak)
for x in citaj[3:]:
    if (x[0] == "<"):
        trenutni_kljuc = x.strip("\n")
        # if (trenutniKljuc not in gramatika.keys()):
        #     gramatika.update({trenutniKljuc: []})
    else:
        # gramatika[trenutniKljuc].append(x.split())
        lijeva_strana = trenutni_kljuc
        desna_strana = ""
        tmp = x.split()
        cnt = 0
        for k in tmp:
            desna_strana += k
            if (cnt < len(tmp) - 1):
                desna_strana += ", "
            cnt += 1

        # produkcije.append(lijevaStrana + " -> " + desnaStrana)
        produkcije_tuple.append((lijeva_strana, x.split()))

        if (lijeva_strana not in stanje_na_produkcije.keys()):
            stanje_na_produkcije[lijeva_strana] = []
        stanje_na_produkcije[lijeva_strana].append(len(produkcije_tuple) - 1)

# print(produkcije_tuple)
# print(stanje_na_produkcije)

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

# print("znakovi su: ", end = "")
# print(znakovi)

# print(produkcije_tuple)
# print(stanje)
kreirano_je_novo_stanje = True

neobradene_produkcije = [pocetno_stanje]

while (neobradene_produkcije != []):

    # ubaci u stanje sve epsilon prijelaze
    for x in stanje:
        produkcija = produkcije_tuple[x[0]]
        # print("produkcije je {}".format(produkcija))
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
        # print(stanje)
        # print(unikati)
    del (neobradene_produkcije[0])
    # prije DKA append ide provjera jel to stanje vec postoji
    # >> da prodes po tuple[1] i usporedis sa vec postojecima u DKA
if (postoji_stanje_u_DKA(stanje, DKA) == False):
    DKA.append((brojac_stanja, stanje))
    brojac_stanja += 1
    kreirano_je_novo_stanje = True
    ### do ovdje je kreirano stanje

for znak in zavrsni_znakovi - {"$"}:
    for x in stanje:
        # print("x je {}".format(x))
        br_produkcije = x[0]
        indeks_tocke = x[1]

        produkcija_za_prijelaz = produkcije_tuple[br_produkcije]
        iduci_znak_u_produkciji = produkcija_za_prijelaz[1][indeks_tocke]
        # print("{} {}".format(produkcija_za_prijelaz, iduci_znak_u_produkciji))

        novo_stanje = []
        if (iduci_znak_u_produkciji == znak):
            # print("jeste: {}".format(iduci_znak_u_produkciji))
            # print("iduci znak: {}".format(iduci_znak_u_produkciji))
            novo_stanje.append((br_produkcije, indeks_tocke + 1, "@@@"))

    if (postoji_stanje_u_DKA(novo_stanje, DKA) == False):
        # print("NOVO STANJE!")
        DKA.append((brojac_stanja, novo_stanje))
        brojac_stanja += 1
        kreirano_je_novo_stanje = True

for x in DKA:
    print(x)

print(produkcije_tuple)

# def izravno_prazan(nezavrsni_znak):
#     for produkcija in stanje_na_produkcije[nezavrsni_znak]:
#         if produkcije_tuple[produkcija][1][0] == "$":
#             return True
#     return False
#
#
# izravno_prazni_znakovi = {izravno_prazan(s) for s in nezavrsniZnakovi}

prazni_znakovi = set()


def je_li_znak_prazan(nezavrsni_znak, visited):
    if (nezavrsni_znak in visited):
        return nezavrsni_znak in prazni_znakovi

    for produkcija in stanje_na_produkcije[nezavrsni_znak]:

        if produkcije_tuple[produkcija][1][0] == "$":
            prazni_znakovi.add(nezavrsni_znak)
            break

        for znak in produkcije_tuple[produkcija][1]:
            if znak in zavrsni_znakovi:
                break

            if znak == nezavrsni_znak:
                break  # TODO not sure if this is correct

            if je_li_znak_prazan(znak, visited):
                prazni_znakovi.add(nezavrsni_znak)
                break

    visited.add(nezavrsni_znak)
    return nezavrsni_znak in prazni_znakovi


visited = set()
for nezavrsni_znak in nezavrsni_znakovi:
    if nezavrsni_znak not in visited:
        je_li_znak_prazan(nezavrsni_znak, visited)


# alpha je niz (lista) od nezavrsnih/zavrsnih znakova
def prazan_skup(alpha):
    # return true ako se alpha moze pretvoriti u $
    # alpha smi biti prazan array
    if len(alpha) == 0:
        return True

    prvi = alpha[0]

    if prvi == "$":
        return True

    if prvi in zavrsni_znakovi:
        return False

    if prvi not in prazni_znakovi:
        return False

    return prazan_skup(alpha[1:])


zapocinje = {}


def init_zapocinje(nezavrsni_znak):
    if nezavrsni_znak not in zapocinje:
        zapocinje[nezavrsni_znak] = set()

    for produkcija in stanje_na_produkcije[nezavrsni_znak]:
        desna_strana = produkcije_tuple[produkcija][1]

        for znak in desna_strana:
            if znak == "$":
                break

            if znak in zavrsni_znakovi:
                zapocinje[nezavrsni_znak].add(znak)
                break

            zapocinje[nezavrsni_znak].update(init_zapocinje(znak))
            if znak not in prazni_znakovi:
                break

    return zapocinje[nezavrsni_znak]


init_zapocinje(NNZ)
for nezavrsni_znak in nezavrsni_znakovi:
    if nezavrsni_znak not in zapocinje:
        init_zapocinje(nezavrsni_znak)


def zapocinje_alpha(alpha):
    skup = set()

    if len(alpha) == 0:
        return skup

    if alpha[0] in zavrsni_znakovi:
        skup.add(alpha[0])
    else:
        skup.update(zapocinje[alpha[0]])
        if (alpha[0] in prazni_znakovi):
            skup.update(zapocinje_alpha(alpha[1:]))

    return skup


def odredi_kontekst_ntorke(ntorka):
    # odredi kontekst prijelaza iza tocke
    # na primjer za ntorku (A->aBc, 1, {x})
    # mora vratiti set {c}
    # a za ntorku (A->aB, 1 , {x})
    # mora vratiti set {x}

    desna_strana = produkcije_tuple[ntorka[0]][1]
    assert (not ntorka[1] > len(desna_strana))  # nesmi tocka bit na kraju, nema smisla tada pozivati..
    assert (produkcije_tuple[ntorka[0]][1][
                ntorka[1]] in nezavrsni_znakovi)  # nema smisla traziti kontekst ako ispred tocke nije nezavrsni znak

    alpha = desna_strana[ntorka[1] + 1:]

    return kontekst(alpha, ntorka[2])


def kontekst(alpha, parent_context):
    result = zapocinje_alpha(alpha)

    if prazan_skup(alpha):
        result.update(parent_context)

    return result


if filename == "gramatikaPrimjer.txt":  # testovi za "gramatikaPrimjer.txt" iz uputa/materijala
    assert (kontekst(['a', 'b', 'c'], ['@']) == set(["a"]))
    assert (kontekst(['<A>', 'b', 'c'], ['@']) == set(["a", "b", "c", "d", "e"]))
    assert (kontekst(['<B>', 'b', 'c'], ['@']) == set(["b"]))
    assert (kontekst(['<C>', 'b', 'c'], ['@']) == set(["a", "c", "d", "b"]))
    assert (kontekst(['<D>', 'b', 'c'], ['@']) == set(["d", "b"]))
    assert (kontekst(['<E>', 'b', 'c'], ['@']) == set(["c", "e"]))
    assert (kontekst(['<B>'], ['@']) == set(["b", "@"]))

    assert (odredi_kontekst_ntorke((0, 0, "@")) == set(["@"]))
    # odredi_kontekst_ntorke((0, 1, "@"))  # ne smi raditi!
    assert (odredi_kontekst_ntorke((1, 0, "@")) == set(["a", "c", "d"]))
    # odredi_kontekst_ntorke((1, 2, "@")) # ne smi raditi!
    assert (odredi_kontekst_ntorke((3, 2, "@")) == set(["c", "e"]))
    assert (odredi_kontekst_ntorke((4, 1, "@")) == set(["b", "@"]))
    assert (odredi_kontekst_ntorke((4, 2, "@")) == set(["@"]))
    assert (odredi_kontekst_ntorke((9, 1, "@")) == set(["f"]))

    print("All context tests passed!")

# ispis
out = open("out", "w")

for x in nezavrsni_znakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in zavrsni_znakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
for x in sinkronizacijski_znakovi:
    out.write(x + "\n")
out.write("#" * 10 + "\n")
# for x in produkcije:
#     out.write(x + "\n")

### TODO
# - preimenovate stanje_na_produkcije

if __name__ == '__main__':
    pass
