class Gramatika:
    """
    Razred koji modelira gramatiku koju koristi generator sintaksnog analizatora.
    Konstruktoru treba dati linije ulaza u formatu opisanom u uputama/materijalima.
    Konstruktor uvijek doda novi pocetni nezavrsni znak.
    Najbitnije je primjetiti da nudi sljedece varijable i metode:
        - pocetni_nezavrsni_znak
        - nezavrsni_znakovi
        - zavrsni_znakovi
        - sinkronizacijski_znakovi
        - produkcije
        - znak_na_produkcije
        - odredi_kontekst_ntorke(ntorka): set zavrsnih znakova
    """

    def __init__(self, lines):
        """
        :param lines: lines containing the definition of the grammar
        """
        self.nezavrsni_znakovi = lines[0].split()[1:]
        self.pocetni_nezavrsni_znak = self.nezavrsni_znakovi[0]
        self.__prazni_znakovi = set()

        self.zavrsni_znakovi = set()
        self.zavrsni_znakovi.add("$")
        self.zavrsni_znakovi.update(lines[1].split()[1:])

        self.sinkronizacijski_znakovi = lines[2].split()[1:]

        self.__init_productions(lines)

        visited = set()
        for nezavrsni_znak in self.nezavrsni_znakovi:
            if nezavrsni_znak not in visited:
                self.__je_li_znak_prazan(nezavrsni_znak, visited)

        self.__zapocinje = {}
        self.__init_zapocinje(self.pocetni_nezavrsni_znak)
        for nezavrsni_znak in self.nezavrsni_znakovi:
            if nezavrsni_znak not in self.__zapocinje:
                self.__init_zapocinje(nezavrsni_znak)

    def __init_productions(self, lines):
        self._novi_pocetni_nezavrsni_znak = "<Novo@Pocetno@Stanje>"
        self.znak_na_produkcije = {}
        self.produkcije = []

        trenutni_kljuc = ""
        lines.insert(3, self._novi_pocetni_nezavrsni_znak)
        lines.insert(4, " " + self.pocetni_nezavrsni_znak)
        for x in lines[3:]:
            if x[0] == "<":
                trenutni_kljuc = x.strip("\n")
            else:
                lijeva_strana = trenutni_kljuc
                desna_strana = ""
                tmp = x.split()
                cnt = 0
                for k in tmp:
                    desna_strana += k
                    if cnt < len(tmp) - 1:
                        desna_strana += ", "
                    cnt += 1

                self.produkcije.append((lijeva_strana, x.split()))

                if lijeva_strana not in self.znak_na_produkcije.keys():
                    self.znak_na_produkcije[lijeva_strana] = []
                self.znak_na_produkcije[lijeva_strana].append(len(self.produkcije) - 1)

    def __je_li_znak_prazan(self, nezavrsni_znak, visited):
        if nezavrsni_znak in visited:
            return nezavrsni_znak in self.__prazni_znakovi

        for produkcija in self.znak_na_produkcije[nezavrsni_znak]:

            if self.produkcije[produkcija][1][0] == "$":
                self.__prazni_znakovi.add(nezavrsni_znak)
                break

            for znak in self.produkcije[produkcija][1]:
                if znak in self.zavrsni_znakovi:
                    break

                if znak == nezavrsni_znak:
                    break

                if self.__je_li_znak_prazan(znak, visited):
                    self.__prazni_znakovi.add(nezavrsni_znak)
                    break

        visited.add(nezavrsni_znak)
        return nezavrsni_znak in self.__prazni_znakovi

    def __prazan_skup(self, alpha):
        """
        :param alpha: niz (lista) od nezavrsnih/zavrsnih znakova. smi biti prazan array
        :return: True ako se alpha moze pretvoriti u $
        """
        if len(alpha) == 0:
            return True

        prvi = alpha[0]

        if prvi == "$":
            return True

        if prvi in self.zavrsni_znakovi:
            return False

        if prvi not in self.__prazni_znakovi:
            return False

        return self.__prazan_skup(alpha[1:])

    def __init_zapocinje(self, nezavrsni_znak):
        """
        Pomocna metoda koja inicijalizira zapocinje skup danog nezavrsnog znaka

        :param nezavrsni_znak:
        :return:
        """
        if nezavrsni_znak not in self.__zapocinje:
            self.__zapocinje[nezavrsni_znak] = set()

        for produkcija in self.znak_na_produkcije[nezavrsni_znak]:
            desna_strana = self.produkcije[produkcija][1]

            for znak in desna_strana:
                if znak == "$":
                    break

                if znak in self.zavrsni_znakovi:
                    self.__zapocinje[nezavrsni_znak].add(znak)
                    break

                if znak != nezavrsni_znak:
                    self.__zapocinje[nezavrsni_znak].update(self.__init_zapocinje(znak))

                if znak not in self.__prazni_znakovi:
                    break

        return self.__zapocinje[nezavrsni_znak]

    def __zapocinje_alpha(self, alpha):
        """
        Pomocna metoda koja odredjuje kojim zavrsnim znakovima zapocinje dana alpha

        :param alpha: niz (lista) znakova, bilo zavrsnih, bilo nezavrsnih, za koju se zeli
            dohvatiti skup zapocni
        :return: set zavrsnih znakova kojima zapocinje dana alpha
        """
        skup = set()

        if len(alpha) == 0:
            return skup

        if alpha[0] in self.zavrsni_znakovi:
            skup.add(alpha[0])
        else:
            skup.update(self.__zapocinje[alpha[0]])
            if alpha[0] in self.__prazni_znakovi:
                skup.update(self.__zapocinje_alpha(alpha[1:]))

        return skup

    def _kontekst(self, alpha, parent_context):
        """
        Pomocna metoda koja dohvaca kontekst za danu alphu
        i context ntorke za koju se zeli izracunati kontekst
        """
        result = self.__zapocinje_alpha(alpha)

        if self.__prazan_skup(alpha):
            result.update(parent_context)

        return result

    def odredi_kontekst_ntorke(self, ntorka):
        """
        Metoda koja odredjuje kontekst na temelju znakova iza tocke.
        Na primjer za ntorku (A->aBc, 1, {x}) mora vratiti set {c}
        a za ntorku (A->aB, 1 , {x}) mora vratiti set {x}

        :param ntorka: predstavlja entorku u obliku (broj_produkcije, mjesto_tocke, kontekst)
        :return: set zavrsnih znakova
        :rtype: set
        """

        desna_strana = self.produkcije[ntorka[0]][1]

        # nesmi tocka bit na kraju, nema smisla tada pozivati..
        assert (not ntorka[1] > len(desna_strana))
        # nema smisla traziti kontekst ako ispred tocke nije nezavrsni znak
        assert (self.produkcije[ntorka[0]][1][ntorka[1]] in self.nezavrsni_znakovi)

        alpha = desna_strana[ntorka[1] + 1:]
        return self._kontekst(alpha, ntorka[2].split(" "))


if __name__ == '__main__':
    ### TESTING ###

    with open("gramatikaPrimjer.txt") as file:
        grammar = Gramatika(file.readlines())

    assert (grammar._kontekst(['a', 'b', 'c'], ['@']) == {"a"})
    assert (grammar._kontekst(['<A>', 'b', 'c'], ['@']) == {"a", "b", "c", "d", "e"})
    assert (grammar._kontekst(['<B>', 'b', 'c'], ['@']) == {"b"})
    assert (grammar._kontekst(['<C>', 'b', 'c'], ['@']) == {"a", "c", "d", "b"})
    assert (grammar._kontekst(['<D>', 'b', 'c'], ['@']) == {"d", "b"})
    assert (grammar._kontekst(['<E>', 'b', 'c'], ['@']) == {"c", "e"})
    assert (grammar._kontekst(['<B>'], ['@']) == {"b", "@"})

    assert (grammar.odredi_kontekst_ntorke((0, 0, "@")) == {"@"})
    # odredi_kontekst_ntorke((0, 1, "@"))  # nesmi raditi!
    assert (grammar.odredi_kontekst_ntorke((1, 0, "@")) == {"a", "c", "d"})
    # odredi_kontekst_ntorke((1, 2, "@")) # nesmi raditi!
    assert (grammar.odredi_kontekst_ntorke((3, 2, "@")) == {"c", "e"})
    assert (grammar.odredi_kontekst_ntorke((4, 1, "@")) == {"b", "@"})
    assert (grammar.odredi_kontekst_ntorke((4, 2, "@")) == {"@"})
    assert (grammar.odredi_kontekst_ntorke((9, 1, "@")) == {"f"})

    print("All context tests passed!")
