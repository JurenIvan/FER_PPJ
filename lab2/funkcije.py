def postoji_stanje_u_DKA(stanje, DKA):
    for x in DKA:
        if (stanje == x[1]):
            return True
    return False

def analiziraj_nezavrsni_znak(produkcije, nezavrsni_znakovi):
    dodana_nova_produkcija = True
    while (dodana_nova_produkcija):
        dodana_nova_produkcija = False

