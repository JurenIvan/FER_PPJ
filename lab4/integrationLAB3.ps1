# m43
$dir_array = "01_idn" , "02_broj", "03_niz_znakova", "04_pogresan_main", "05_impl_int2char", "06_nedekl_fun", "07_nedef_fun", "08_ne_arg", "09_fun_povtip", "10_fun_params", "11_niz", "12_fun_niz", "13_lval1", "14_lval2", "15_cast1", "16_cast2", "17_log", "18_if", "19_cont_brk", "20_ret_void", "21_ret_nonvoid", "22_fun_multidef", "23_rek", "24_param_dekl", "25_fun_dekl_def", "26_multi_dekl", "27_dekl_odmah_aktivna", "28_niz_init", "29_for", "30_const_init"

Foreach ($dir in $dir_array)
{
    $current = ".\data\testsLAB3\" + $dir + "\test"
    $current
    Get-Content ($current+".in") | java -cp target/classes semanal.SemantickiAnalizator > output.out
    Compare-Object (Get-Content output.out) (Get-Content ($current+".out"))
}

$dir_array = "01_ret_broj", "02_ret_global", "03_veliki_broj", "04_neg_broj", "05_plus", "06_plus_signed", "07_minus", "08_bitor", "09_bitand", "10_bitxor", "11_fun1", "12_fun2", "13_fun3", "13_scope1", "14_scope2", "15_scope3", "16_scope4", "17_char", "18_init_izraz", "19_if1", "20_if2", "21_if3", "22_if4", "23_niz1", "24_niz2", "25_niz3", "26_niz4", "27_rek", "28_rek_main", "29_for", "30_while", "31_inc", "32_gcd", "33_short", "34_izraz", "35_params", "36_params2", "37_funcloop", "integration1", "integration2"
Foreach ($dir in $dir_array)
{
    $current = ".\data\tests\" + $dir + "\test"
    $current
    Get-Content ($current+".in") | java -cp target/classes semanal.SemantickiAnalizator > output.out
    Compare-Object (Get-Content output.out) (echo "`0")
}