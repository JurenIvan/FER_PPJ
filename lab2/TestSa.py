import os
import time

def runTests(test):
	os.system('python3 GSA.py < tests/{}/test.san'.format(test))
	os.system('python3 SA.py < tests/{}/test.in > data.out'.format(test))
	os.system('diff data.out tests/{}/test.out '.format(test))

tests = ['00_github__kanon_gramatika', '00_github__minusLang', '00_github__simplePpjLang_err', '00_github__simplePpjLang_manji', '00_github__simplePpjLang_najmanji', '00_github__simplePpjLang_veci', '00aab_1', '01aab_2', '02aab_3', '03gram100_1', '04gram100_2', '05gram100_3', '06oporavak1', '07oporavak2', '08pomred', '09redred', '10minusLang_1', '11minusLang_2', '12ppjC', '13ppjLang', '14simplePpjLang']
for test in tests:
	print(test)
	runTests(test)