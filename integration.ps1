# m43
$dir_array = "svaki_drugi_a2", "svaki_drugi_a1", "simplePpjLang", "nadji_a1", "nadji_a2", "minusLang", "greske_u_stanju","minusLang_laksi","minusLang_tezi","nadji_x","nadji_x_oporavak","nadji_x_retci","poredak","ppjLang_laksi","ppjLang_tezi","regex_escapes","regex_laksi","regex_regdefs","regex_tezi","simplePpjLang_laksi","simplePpjLang_tezi","state_hopper","svaki_treci_x","vrati_se","vrati_se_prioritet", "duljina"

Foreach ($dir in $dir_array)
{
    $current = ".\data\lab1\tests\" + $dir + "\test"
    $current
    Get-Content ($current+".lan") | java -cp target/classes lexer.GLA
    Get-Content ($current+".in") | java -cp target/classes lexer.LA > output.out
    Compare-Object (Get-Content output.out) (Get-Content ($current+".out"))
}