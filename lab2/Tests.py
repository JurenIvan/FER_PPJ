import unittest

from Gramatika import *

test3 = """%V <S> <A> <B>
%T a b
%Syn a
<S>
 <A> <A> <S>
 <B> <S>
 <A> <A>
 <B>
<A>
 a
<B>
 b
"""

test4 = """%V <S> <A> <B>
%T a b
%Syn b
<S>
 <A> <A> <S>
 <B> <S>
 <A> <A>
 <B>
<A>
 a
<B>
 b
"""

test5 = """%V <S> <A> <B>
%T a b
%Syn a
<S>
 <A> <A> <S>
 <B> <S>
 <A> <A>
 <B>
<A>
 a
<B>
 b
"""

test6 = """%V <S> <A> <B>
%T a b
%Syn a
<S>
 <A> <A> <S>
 <B> <S>
 <A> <A>
 <B>
<A>
 a
<B>
 b
"""

test7 = """%V <A> <B> <C> <D> <E>
%T a b c d e f
%Syn a
<A>
 <B> <C> c
<B>
 $
 b <C> <D> <E>
<A>
 e <D> <B>
<C>
 <D> a <B>
 c a
<D>
 $
 d <D>
<E>
 e <A> f
 c
"""

test8 = """%V <A> <B> <C> <D> <E>
%T a b c d e f
%Syn a
<A>
 <B> <C> c
<B>
 $
 b <C> <D> <E>
<A>
 e <D> <B>
<C>
 <D> a <B>
 c a
<D>
 $
 d <D>
<E>
 e <A> f
 c
"""

test9 = """%V <S> <A> <B>
%T a b
%Syn a
<S>
 <B>
 <A>
<B>
 a a b
<A>
 a a b
"""

test10 = """%V <expr> <atom>
%T OPERAND OP_MINUS UMINUS LIJEVA_ZAGRADA DESNA_ZAGRADA
%Syn OPERAND UMINUS LIJEVA_ZAGRADA
<atom>
 OPERAND
 UMINUS <atom>
 LIJEVA_ZAGRADA <expr> DESNA_ZAGRADA
<expr>
 <atom>
 <expr> OP_MINUS <atom>
"""

test11 = """%V <prijevodna_jedinica> <vanjska_deklaracija> <deklaracija> <definicija_funkcije> <specifikatori_deklaracije> <deklarator> <primarni_izraz> <izraz> <postfiks_izraz> <lista_argumenata> <izraz_pridruzivanja> <unarni_izraz> <unarni_operator> <cast_izraz> <ime_tipa> <multiplikativni_izraz> <aditivni_izraz> <odnosni_izraz> <jednakosni_izraz> <bin_i_izraz> <bin_xili_izraz> <bin_ili_izraz> <log_i_izraz> <log_ili_izraz> <specifikator_tipa> <lista_init_deklaratora> <init_deklarator> <inicijalizator> <struct_specifikator> <struct_lista_deklaracija> <struct_deklaracija> <lista_specifikatora_kvalifikatora> <struct_lista_deklaratora> <struct_deklarator> <pokazivac> <izravni_deklarator> <lista_parametara> <deklaracija_parametra> <lista_izraza_pridruzivanja> <naredba> <slozena_naredba> <izraz_naredba> <naredba_grananja> <naredba_petlje> <naredba_skoka> <lista_naredbi> <lista_deklaracija>
%T IDN BROJ ZNAK NIZ_ZNAKOVA KR_BREAK KR_CHAR KR_CONST KR_CONTINUE KR_ELSE KR_FLOAT KR_FOR KR_IF KR_INT KR_RETURN KR_STRUCT KR_VOID KR_WHILE PLUS OP_INC MINUS OP_DEC ASTERISK OP_DIJELI OP_MOD OP_PRIDRUZI OP_LT OP_LTE OP_GT OP_GTE OP_EQ OP_NEQ OP_NEG OP_TILDA OP_I OP_ILI AMPERSAND OP_BIN_ILI OP_BIN_XILI ZAREZ TOCKAZAREZ TOCKA L_ZAGRADA D_ZAGRADA L_UGL_ZAGRADA D_UGL_ZAGRADA L_VIT_ZAGRADA D_VIT_ZAGRADA
%Syn TOCKAZAREZ D_VIT_ZAGRADA
<primarni_izraz>
 IDN
 BROJ
 ZNAK
 NIZ_ZNAKOVA
 L_ZAGRADA <izraz> D_ZAGRADA
<postfiks_izraz>
 <primarni_izraz>
 <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
 <postfiks_izraz> L_ZAGRADA D_ZAGRADA
 <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
 <postfiks_izraz> TOCKA IDN
 <postfiks_izraz> OP_INC
 <postfiks_izraz> OP_DEC
<lista_argumenata>
 <izraz_pridruzivanja>
 <lista_argumenata> ZAREZ <izraz_pridruzivanja>
<unarni_izraz>
 <postfiks_izraz>
 OP_INC <unarni_izraz>
 OP_DEC <unarni_izraz>
 <unarni_operator> <cast_izraz>
<unarni_operator>
 AMPERSAND
 ASTERISK
 PLUS
 MINUS
 OP_TILDA
 OP_NEG
<cast_izraz>
 <unarni_izraz>
 L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
<multiplikativni_izraz>
 <cast_izraz>
 <multiplikativni_izraz> ASTERISK <cast_izraz>
 <multiplikativni_izraz> OP_DIJELI <cast_izraz>
 <multiplikativni_izraz> OP_MOD <cast_izraz>
<aditivni_izraz>
 <multiplikativni_izraz>
 <aditivni_izraz> PLUS <multiplikativni_izraz>
 <aditivni_izraz> MINUS <multiplikativni_izraz>
<odnosni_izraz>
 <aditivni_izraz>
 <odnosni_izraz> OP_LT <aditivni_izraz>
 <odnosni_izraz> OP_GT <aditivni_izraz>
 <odnosni_izraz> OP_LTE <aditivni_izraz>
 <odnosni_izraz> OP_GTE <aditivni_izraz>
<jednakosni_izraz>
 <odnosni_izraz>
 <jednakosni_izraz> OP_EQ <odnosni_izraz>
 <jednakosni_izraz> OP_NEQ <odnosni_izraz>
<bin_i_izraz>
 <jednakosni_izraz>
 <bin_i_izraz> AMPERSAND <jednakosni_izraz>
<bin_xili_izraz>
 <bin_i_izraz>
 <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
<bin_ili_izraz>
 <bin_xili_izraz>
 <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
<log_i_izraz>
 <bin_ili_izraz>
 <log_i_izraz> OP_I <bin_ili_izraz>
<log_ili_izraz>
 <log_i_izraz>
 <log_ili_izraz> OP_ILI <log_i_izraz>
<izraz_pridruzivanja>
 <log_ili_izraz>
 <unarni_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
<izraz>
 <izraz_pridruzivanja>
 <izraz> ZAREZ <izraz_pridruzivanja>
<deklaracija>
 <specifikatori_deklaracije> TOCKAZAREZ
 <specifikatori_deklaracije> <lista_init_deklaratora> TOCKAZAREZ
<specifikatori_deklaracije>
 <specifikator_tipa>
 <specifikator_tipa> <specifikatori_deklaracije>
 KR_CONST
 KR_CONST <specifikatori_deklaracije>
<lista_init_deklaratora>
 <init_deklarator>
 <lista_init_deklaratora> ZAREZ <init_deklarator>
<init_deklarator>
 <deklarator>
 <deklarator> OP_PRIDRUZI <inicijalizator>
<specifikator_tipa>
 KR_VOID
 KR_CHAR
 KR_INT
 KR_FLOAT
 <struct_specifikator>
<struct_specifikator>
 KR_STRUCT IDN L_VIT_ZAGRADA <struct_lista_deklaracija> D_VIT_ZAGRADA
 KR_STRUCT L_VIT_ZAGRADA <struct_lista_deklaracija> D_VIT_ZAGRADA
 KR_STRUCT IDN
<struct_lista_deklaracija>
 <struct_deklaracija>
 <struct_lista_deklaracija> <struct_deklaracija>
<struct_deklaracija>
 <lista_specifikatora_kvalifikatora> <struct_lista_deklaratora> TOCKAZAREZ
<lista_specifikatora_kvalifikatora>
 <specifikator_tipa> <lista_specifikatora_kvalifikatora>
 <specifikator_tipa>
 KR_CONST <lista_specifikatora_kvalifikatora>
 KR_CONST
<struct_lista_deklaratora>
 <struct_deklarator>
 <struct_lista_deklaratora> ZAREZ <struct_deklarator>
<struct_deklarator>
 <deklarator>
<deklarator>
 <pokazivac> <izravni_deklarator>
 <izravni_deklarator>
<izravni_deklarator>
 IDN
 <izravni_deklarator> L_UGL_ZAGRADA <log_ili_izraz> D_UGL_ZAGRADA
 <izravni_deklarator> L_UGL_ZAGRADA D_UGL_ZAGRADA
 <izravni_deklarator> L_ZAGRADA <lista_parametara> D_ZAGRADA
<pokazivac>
 ASTERISK
 ASTERISK KR_CONST
<lista_parametara>
 <deklaracija_parametra>
 <lista_parametara> ZAREZ <deklaracija_parametra>
<deklaracija_parametra>
 <specifikatori_deklaracije> <deklarator>
 <specifikatori_deklaracije>
<ime_tipa>
 <lista_specifikatora_kvalifikatora>
 <lista_specifikatora_kvalifikatora> <pokazivac>
<inicijalizator>
 <izraz_pridruzivanja>
 L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_izraza_pridruzivanja> ZAREZ D_VIT_ZAGRADA
<lista_izraza_pridruzivanja>
 <izraz_pridruzivanja>
 <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
<naredba>
 <slozena_naredba>
 <izraz_naredba>
 <naredba_grananja>
 <naredba_petlje>
 <naredba_skoka>
<slozena_naredba>
 L_VIT_ZAGRADA D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_deklaracija> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA
<lista_deklaracija>
 <deklaracija>
 <lista_deklaracija> <deklaracija>
<lista_naredbi>
 <naredba>
 <lista_naredbi> <naredba>
<izraz_naredba>
 TOCKAZAREZ
 <izraz> TOCKAZAREZ
<naredba_grananja>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>
<naredba_petlje>
 KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>
<naredba_skoka>
 KR_CONTINUE TOCKAZAREZ
 KR_BREAK TOCKAZAREZ
 KR_RETURN TOCKAZAREZ
 KR_RETURN <izraz> TOCKAZAREZ
<prijevodna_jedinica>
 <vanjska_deklaracija>
 <prijevodna_jedinica> <vanjska_deklaracija>
<vanjska_deklaracija>
 <definicija_funkcije>
 <deklaracija>
<definicija_funkcije>
 <specifikatori_deklaracije> <deklarator> <lista_deklaracija> <slozena_naredba>
 <specifikatori_deklaracije> <deklarator> <slozena_naredba>
 <deklarator> <lista_deklaracija> <slozena_naredba>
 <deklarator> <slozena_naredba>
"""

test12 = """%V <prijevodna_jedinica> <vanjska_deklaracija> <deklaracija> <definicija_funkcije> <specifikatori_deklaracije> <primarni_izraz> <izraz> <postfiks_izraz> <lista_argumenata> <izraz_pridruzivanja> <unarni_izraz> <unarni_operator> <cast_izraz> <ime_tipa> <multiplikativni_izraz> <aditivni_izraz> <odnosni_izraz> <jednakosni_izraz> <bin_i_izraz> <bin_xili_izraz> <bin_ili_izraz> <log_i_izraz> <log_ili_izraz> <specifikator_tipa> <lista_init_deklaratora> <init_deklarator> <inicijalizator> <lista_specifikatora_kvalifikatora> <izravni_deklarator> <lista_parametara> <deklaracija_parametra> <lista_izraza_pridruzivanja> <naredba> <slozena_naredba> <izraz_naredba> <naredba_grananja> <naredba_petlje> <naredba_skoka> <lista_naredbi> <lista_deklaracija>
%T IDN BROJ ZNAK NIZ_ZNAKOVA KR_BREAK KR_CHAR KR_CONST KR_CONTINUE KR_ELSE KR_FOR KR_IF KR_INT KR_RETURN KR_VOID KR_WHILE PLUS OP_INC MINUS OP_DEC OP_PUTA OP_DIJELI OP_MOD OP_PRIDRUZI OP_LT OP_LTE OP_GT OP_GTE OP_EQ OP_NEQ OP_NEG OP_TILDA OP_I OP_ILI OP_BIN_I OP_BIN_ILI OP_BIN_XILI ZAREZ TOCKAZAREZ L_ZAGRADA D_ZAGRADA L_UGL_ZAGRADA D_UGL_ZAGRADA L_VIT_ZAGRADA D_VIT_ZAGRADA
%Syn TOCKAZAREZ D_VIT_ZAGRADA
<prijevodna_jedinica>
 <vanjska_deklaracija>
 <prijevodna_jedinica> <vanjska_deklaracija>
<vanjska_deklaracija>
 <definicija_funkcije>
 <deklaracija>
<definicija_funkcije>
 <specifikatori_deklaracije> <izravni_deklarator> <slozena_naredba>
<slozena_naredba>
 L_VIT_ZAGRADA D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_deklaracija> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA
<lista_deklaracija>
 <deklaracija>
 <lista_deklaracija> <deklaracija>
<lista_naredbi>
 <naredba>
 <lista_naredbi> <naredba>
<naredba>
 <slozena_naredba>
 <izraz_naredba>
 <naredba_grananja>
 <naredba_petlje>
 <naredba_skoka>
<izraz_naredba>
 TOCKAZAREZ
 <izraz> TOCKAZAREZ
<naredba_grananja>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>
<naredba_petlje>
 KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>
<naredba_skoka>
 KR_CONTINUE TOCKAZAREZ
 KR_BREAK TOCKAZAREZ
 KR_RETURN TOCKAZAREZ
 KR_RETURN <izraz> TOCKAZAREZ
<primarni_izraz>
 IDN
 BROJ
 ZNAK
 NIZ_ZNAKOVA
 L_ZAGRADA <izraz> D_ZAGRADA
<postfiks_izraz>
 <primarni_izraz>
 <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
 <postfiks_izraz> L_ZAGRADA D_ZAGRADA
 <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
 <postfiks_izraz> OP_INC
 <postfiks_izraz> OP_DEC
<lista_argumenata>
 <izraz_pridruzivanja>
 <lista_argumenata> ZAREZ <izraz_pridruzivanja>
<unarni_izraz>
 <postfiks_izraz>
 OP_INC <unarni_izraz>
 OP_DEC <unarni_izraz>
 <unarni_operator> <cast_izraz>
<unarni_operator>
 PLUS
 MINUS
 OP_TILDA
 OP_NEG
<cast_izraz>
 <unarni_izraz>
 L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
<ime_tipa>
 <lista_specifikatora_kvalifikatora>
<lista_specifikatora_kvalifikatora>
 <specifikator_tipa> <lista_specifikatora_kvalifikatora>
 <specifikator_tipa>
 KR_CONST <lista_specifikatora_kvalifikatora>
 KR_CONST
<specifikator_tipa>
 KR_VOID
 KR_CHAR
 KR_INT
<multiplikativni_izraz>
 <cast_izraz>
 <multiplikativni_izraz> OP_PUTA <cast_izraz>
 <multiplikativni_izraz> OP_DIJELI <cast_izraz>
 <multiplikativni_izraz> OP_MOD <cast_izraz>
<aditivni_izraz>
 <multiplikativni_izraz>
 <aditivni_izraz> PLUS <multiplikativni_izraz>
 <aditivni_izraz> MINUS <multiplikativni_izraz>
<odnosni_izraz>
 <aditivni_izraz>
 <odnosni_izraz> OP_LT <aditivni_izraz>
 <odnosni_izraz> OP_GT <aditivni_izraz>
 <odnosni_izraz> OP_LTE <aditivni_izraz>
 <odnosni_izraz> OP_GTE <aditivni_izraz>
<jednakosni_izraz>
 <odnosni_izraz>
 <jednakosni_izraz> OP_EQ <odnosni_izraz>
 <jednakosni_izraz> OP_NEQ <odnosni_izraz>
<bin_i_izraz>
 <jednakosni_izraz>
 <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
<bin_xili_izraz>
 <bin_i_izraz>
 <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
<bin_ili_izraz>
 <bin_xili_izraz>
 <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
<log_i_izraz>
 <bin_ili_izraz>
 <log_i_izraz> OP_I <bin_ili_izraz>
<log_ili_izraz>
 <log_i_izraz>
 <log_ili_izraz> OP_ILI <log_i_izraz>
<izraz_pridruzivanja>
 <log_ili_izraz>
 <unarni_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
<izraz>
 <izraz_pridruzivanja>
 <izraz> ZAREZ <izraz_pridruzivanja>
<deklaracija>
 <specifikatori_deklaracije> TOCKAZAREZ
 <specifikatori_deklaracije> <lista_init_deklaratora> TOCKAZAREZ
<specifikatori_deklaracije>
 <specifikator_tipa>
 <specifikator_tipa> <specifikatori_deklaracije>
 KR_CONST
 KR_CONST <specifikatori_deklaracije>
<lista_init_deklaratora>
 <init_deklarator>
 <lista_init_deklaratora> ZAREZ <init_deklarator>
<init_deklarator>
 <izravni_deklarator>
 <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
<izravni_deklarator>
 IDN
 <izravni_deklarator> L_UGL_ZAGRADA <log_ili_izraz> D_UGL_ZAGRADA
 <izravni_deklarator> L_UGL_ZAGRADA D_UGL_ZAGRADA
 <izravni_deklarator> L_ZAGRADA <lista_parametara> D_ZAGRADA
<lista_parametara>
 <deklaracija_parametra>
 <lista_parametara> ZAREZ <deklaracija_parametra>
<deklaracija_parametra>
 <specifikatori_deklaracije> <izravni_deklarator>
 <specifikatori_deklaracije>
<inicijalizator>
 <izraz_pridruzivanja>
 L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_izraza_pridruzivanja> ZAREZ D_VIT_ZAGRADA
<lista_izraza_pridruzivanja>
 <izraz_pridruzivanja>
 <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
"""

test13 = """%V <prijevodna_jedinica> <primarni_izraz> <postfiks_izraz> <lista_argumenata> <unarni_izraz> <unarni_operator> <cast_izraz> <ime_tipa> <specifikator_tipa> <multiplikativni_izraz> <aditivni_izraz> <odnosni_izraz> <jednakosni_izraz> <bin_i_izraz> <bin_xili_izraz> <bin_ili_izraz> <log_i_izraz> <log_ili_izraz> <izraz_pridruzivanja> <izraz> <slozena_naredba> <lista_naredbi> <naredba> <izraz_naredba> <naredba_grananja> <naredba_petlje> <naredba_skoka> <vanjska_deklaracija> <definicija_funkcije> <lista_parametara> <deklaracija_parametra> <lista_deklaracija> <deklaracija> <lista_init_deklaratora> <init_deklarator> <izravni_deklarator> <inicijalizator> <lista_izraza_pridruzivanja>
%T IDN BROJ ZNAK NIZ_ZNAKOVA KR_BREAK KR_CHAR KR_CONST KR_CONTINUE KR_ELSE KR_FOR KR_IF KR_INT KR_RETURN KR_VOID KR_WHILE PLUS OP_INC MINUS OP_DEC OP_PUTA OP_DIJELI OP_MOD OP_PRIDRUZI OP_LT OP_LTE OP_GT OP_GTE OP_EQ OP_NEQ OP_NEG OP_TILDA OP_I OP_ILI OP_BIN_I OP_BIN_ILI OP_BIN_XILI ZAREZ TOCKAZAREZ L_ZAGRADA D_ZAGRADA L_UGL_ZAGRADA D_UGL_ZAGRADA L_VIT_ZAGRADA D_VIT_ZAGRADA
%Syn TOCKAZAREZ D_VIT_ZAGRADA
<primarni_izraz>
 IDN
 BROJ
 ZNAK
 NIZ_ZNAKOVA
 L_ZAGRADA <izraz> D_ZAGRADA
<postfiks_izraz>
 <primarni_izraz>
 <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
 <postfiks_izraz> L_ZAGRADA D_ZAGRADA
 <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
 <postfiks_izraz> OP_INC
 <postfiks_izraz> OP_DEC
<lista_argumenata>
 <izraz_pridruzivanja>
 <lista_argumenata> ZAREZ <izraz_pridruzivanja>
<unarni_izraz>
 <postfiks_izraz>
 OP_INC <unarni_izraz>
 OP_DEC <unarni_izraz>
 <unarni_operator> <cast_izraz>
<unarni_operator>
 PLUS
 MINUS
 OP_TILDA
 OP_NEG
<cast_izraz>
 <unarni_izraz>
 L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
<ime_tipa>
 <specifikator_tipa>
 KR_CONST <specifikator_tipa>
<specifikator_tipa>
 KR_VOID
 KR_CHAR
 KR_INT
<multiplikativni_izraz>
 <cast_izraz>
 <multiplikativni_izraz> OP_PUTA <cast_izraz>
 <multiplikativni_izraz> OP_DIJELI <cast_izraz>
 <multiplikativni_izraz> OP_MOD <cast_izraz>
<aditivni_izraz>
 <multiplikativni_izraz>
 <aditivni_izraz> PLUS <multiplikativni_izraz>
 <aditivni_izraz> MINUS <multiplikativni_izraz>
<odnosni_izraz>
 <aditivni_izraz>
 <odnosni_izraz> OP_LT <aditivni_izraz>
 <odnosni_izraz> OP_GT <aditivni_izraz>
 <odnosni_izraz> OP_LTE <aditivni_izraz>
 <odnosni_izraz> OP_GTE <aditivni_izraz>
<jednakosni_izraz>
 <odnosni_izraz>
 <jednakosni_izraz> OP_EQ <odnosni_izraz>
 <jednakosni_izraz> OP_NEQ <odnosni_izraz>
<bin_i_izraz>
 <jednakosni_izraz>
 <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
<bin_xili_izraz>
 <bin_i_izraz>
 <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
<bin_ili_izraz>
 <bin_xili_izraz>
 <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
<log_i_izraz>
 <bin_ili_izraz>
 <log_i_izraz> OP_I <bin_ili_izraz>
<log_ili_izraz>
 <log_i_izraz>
 <log_ili_izraz> OP_ILI <log_i_izraz>
<izraz_pridruzivanja>
 <log_ili_izraz>
 <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
<izraz>
 <izraz_pridruzivanja>
 <izraz> ZAREZ <izraz_pridruzivanja>
<slozena_naredba>
 L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
 L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA
<lista_naredbi>
 <naredba>
 <lista_naredbi> <naredba>
<naredba>
 <slozena_naredba>
 <izraz_naredba>
 <naredba_grananja>
 <naredba_petlje>
 <naredba_skoka>
<izraz_naredba>
 TOCKAZAREZ
 <izraz> TOCKAZAREZ
<naredba_grananja>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>
<naredba_petlje>
 KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>
 KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>
<naredba_skoka>
 KR_CONTINUE TOCKAZAREZ
 KR_BREAK TOCKAZAREZ
 KR_RETURN TOCKAZAREZ
 KR_RETURN <izraz> TOCKAZAREZ
<prijevodna_jedinica>
 <vanjska_deklaracija>
 <prijevodna_jedinica> <vanjska_deklaracija>
<vanjska_deklaracija>
 <definicija_funkcije>
 <deklaracija>
<definicija_funkcije>
 <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
 <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA <slozena_naredba>
<lista_parametara>
 <deklaracija_parametra>
 <lista_parametara> ZAREZ <deklaracija_parametra>
<deklaracija_parametra>
 <ime_tipa> IDN
 <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA
<lista_deklaracija>
 <deklaracija>
 <lista_deklaracija> <deklaracija>
<deklaracija>
 <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ
<lista_init_deklaratora>
 <init_deklarator>
 <lista_init_deklaratora> ZAREZ <init_deklarator>
<init_deklarator>
 <izravni_deklarator>
 <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
<izravni_deklarator>
 IDN
 IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
 IDN L_ZAGRADA KR_VOID D_ZAGRADA
 IDN L_ZAGRADA <lista_parametara> D_ZAGRADA
<inicijalizator>
 <izraz_pridruzivanja>
 L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA
<lista_izraza_pridruzivanja>
 <izraz_pridruzivanja>
 <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
"""

test14 = """%V <expr> <atom>
%T OPERAND OP_MINUS UMINUS LIJEVA_ZAGRADA DESNA_ZAGRADA
%Syn OPERAND UMINUS LIJEVA_ZAGRADA
<atom>
 OPERAND
 UMINUS <atom>
 LIJEVA_ZAGRADA <expr> DESNA_ZAGRADA
<expr>
 <atom>
 <expr> OP_MINUS <atom>
"""

test15 = """%V <S>
%T a b
%Syn a
<S>
 a
 a b
 <S> b
"""


class MyTestCase(unittest.TestCase):
    def test_basic_grammar(self):
        basic_grammar_tests()

    def test_if_various_grammars_are_parsed_without_errors(self):
        grammar3 = Gramatika(test3.splitlines())
        assert grammar3

        grammar4 = Gramatika(test4.splitlines())
        assert grammar4

        grammar5 = Gramatika(test5.splitlines())
        assert grammar5

        grammar6 = Gramatika(test6.splitlines())
        assert grammar6

        grammar7 = Gramatika(test7.splitlines())
        assert grammar7

        grammar8 = Gramatika(test8.splitlines())
        assert grammar8

        grammar9 = Gramatika(test9.splitlines())
        assert grammar9

        grammar10 = Gramatika(test10.splitlines())
        assert grammar10

        grammar11 = Gramatika(test11.splitlines())
        assert grammar11

        grammar12 = Gramatika(test12.splitlines())
        assert grammar12

        grammar13 = Gramatika(test13.splitlines())
        assert grammar13

        grammar14 = Gramatika(test14.splitlines())
        assert grammar14
        grammar14 = Gramatika(test14.splitlines())
        assert grammar14
        grammar14 = Gramatika(test14.splitlines())
        assert grammar14

        grammar15 = Gramatika(test15.splitlines())
        assert grammar15


if __name__ == '__main__':
    unittest.main()
