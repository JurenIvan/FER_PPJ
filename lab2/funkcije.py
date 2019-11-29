def postoji_stanje_u_DKA(stanje, DKA):
    for x in DKA:
        if (stanje == x[1]):
            return True
    return False

def stvori_stanje(pocetno_stanje, produkcije_tuple, zavrsni_znakovi, stanje_na_produkcije, stanje):
    odvrti_produkcije = [pocetno_stanje]
    odradene_produkcije = set()
    while (odvrti_produkcije != []):

        odradene_produkcije.add(odvrti_produkcije[0])
        obradi_produkciju = odvrti_produkcije[0][0]
        indeks_tocke = odvrti_produkcije[0][1]
        #print("obradi_produkciju: {}".format(obradi_produkciju))
        dohvati_znak_u_produkciji = produkcije_tuple[obradi_produkciju][1][indeks_tocke]
        #print(dohvati_znak_u_produkciji)
        if (dohvati_znak_u_produkciji in zavrsni_znakovi):
            del(odvrti_produkcije[0])
            continue
        for x in stanje_na_produkcije[dohvati_znak_u_produkciji]:
            ntorka = (x, 0, "@")
            if (ntorka not in odradene_produkcije):
                odvrti_produkcije.append(ntorka)
                odradene_produkcije.add(ntorka)
                stanje.append(ntorka)
        del(odvrti_produkcije[0])

def dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja):
    if (postoji_stanje_u_DKA(stanje, DKA) == False):
        DKA.append((brojac_stanja, stanje))
        brojac_stanja += 1