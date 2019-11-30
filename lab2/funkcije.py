def stvori_stanje(gramatika, pocetne_ntorke, produkcije, zavrsni_znakovi, znak_na_produkcije):
    stanje = []

    odvrti_produkcije = pocetne_ntorke.copy()
    odradene_produkcije = set()
    while odvrti_produkcije != []:
        stanje.append(odvrti_produkcije[0])

        odradene_produkcije.add(odvrti_produkcije[0])
        obradi_produkciju = odvrti_produkcije[0][0]

        indeks_tocke = odvrti_produkcije[0][1]
        desna_strana = produkcije[obradi_produkciju][1]
        if desna_strana[0] == "$" or len(desna_strana) <= indeks_tocke or desna_strana[indeks_tocke] in zavrsni_znakovi:
            del (odvrti_produkcije[0])
            continue

        kontekst = sorted(list(gramatika.odredi_kontekst_ntorke(odvrti_produkcije[0])))
        kontekst_string = " ".join(kontekst)

        for x in znak_na_produkcije[desna_strana[indeks_tocke]]:
            ntorka = (x, 0, kontekst_string)
            if ntorka not in odradene_produkcije:
                odvrti_produkcije.append(ntorka)
                odradene_produkcije.add(ntorka)
        del (odvrti_produkcije[0])

    return sorted(stanje)


def postoji_stanje_u_DKA(stanje, DKA):
    for i in range(len(DKA)):
        if stanje == DKA[i][1]:  # TODO Pomocna struktura koja bi to radila u O(1)
            return DKA[i][0]
    return -1


def dodaj_stanje_u_DKA(stanje, DKA, brojac_stanja):
    postoji_li_stanje = postoji_stanje_u_DKA(stanje, DKA)
    if (postoji_li_stanje == -1):
        DKA.append((brojac_stanja, stanje))
        return brojac_stanja
    return postoji_li_stanje
