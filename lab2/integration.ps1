# m43
$dir_array = "00_github__kanon_gramatika", "00_github__minusLang", "00_github__simplePpjLang_err", "00_github__simplePpjLang_manji", "00_github__simplePpjLang_najmanji", "00_github__simplePpjLang_veci", "00aab_1", "01aab_2", "02aab_3", "03gram100_1", "04gram100_2", "05gram100_3", "06oporavak1", "07oporavak2", "08pomred", "09redred", "10minusLang_1", "11minusLang_2", "12ppjC", "13ppjLang", "14simplePpjLang"

"go.."
Foreach ($dir in $dir_array)
{
    $current = ".\tests\" + $dir + "\test"
    $current
    Get-Content ($current+".san") | python GSA.py
    cd analizator
    Get-Content ("..\"+$current+".in") | python SA.py > output.out
    cd ..
    Compare-Object (Get-Content "analizator\output.out") (Get-Content ($current+".out"))
}