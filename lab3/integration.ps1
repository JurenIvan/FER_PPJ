# m43
$dir_array = "01_idn" , "02_broj", "03_niz_znakova", "04_pogresan_main", "05_impl_int2char", "06_nedekl_fun", "07_nedef_fun", "08_ne_arg", "09_fun_povtip", "10_fun_params", "11_niz", "12_fun_niz", "13_lval1", "14_lval2", "15_cast1", "16_cast2", "17_log", "18_if", "19_cont_brk", "20_ret_void", "21_ret_nonvoid", "22_fun_multidef", "23_rek", "24_param_dekl", "25_fun_dekl_def", "26_multi_dekl", "27_dekl_odmah_aktivna", "28_niz_init", "29_for", "30_const_init"

Foreach ($dir in $dir_array)
{
    $current = ".\data\tests\" + $dir + "\test"
    $current
    Get-Content ($current+".in") | java -cp target/classes semanal.SemantickiAnalizator > output.out
    Compare-Object (Get-Content output.out) (Get-Content ($current+".out"))
}