import os
import time
from shutil import copyfile

global total
total = 0
def runTests(test):
	global total
	os.system('cmd /C "java -cp target\\classes semanal.GeneratorKoda < .\\data\\tests\\{}\\test.in > log.out"'.format(test))
	copyfile('a.frisc', '.\\data\\tests\\{}\\a.frisc'.format(test))
	os.system('node main.js a.frisc > .\\data\\tests\\{}\\result.out'.format(test))
	#os.system('diff data.out data/tests/{}/test.out '.format(test))
	with open('.\\data\\tests\\{}\\test.out'.format(test)) as f1:
		with open('.\\data\\tests\\{}\\result.out'.format(test)) as f2:
			if f1.read().split('\n', 1)[0].strip() == f2.read().split('\n', 1)[0].strip():
				print('✓')
				total += 1
			else:
				print('x')

#tests = ['01_ret_broj', '02_ret_global', '03_veliki_broj', '04_neg_broj', '05_plus', '06_plus_signed', '07_minus', '08_bitor', '09_bitand', '10_bitxor', '11_fun1', '12_fun2', '13_fun3', '13_scope1', '14_scope2', '15_scope3', '16_scope4', '17_char', '18_init_izraz', '19_if1', '20_if2', '21_if3', '22_if4', '23_niz1', '24_niz2', '25_niz3', '26_niz4', '27_rek', '28_rek_main', '29_for', '30_while', '31_inc', '32_gcd', '33_short', '34_izraz', '35_params', '36_params2', '37_funcloop', 'integration1', 'integration2']
tests = ['01_ret_broj', '02_ret_global', '03_veliki_broj', '04_neg_broj', '05_plus', '06_plus_signed', '07_minus', '08_bitor', '09_bitand', '10_bitxor', '11_fun1', '12_fun2', '13_fun3', '13_scope1', '14_scope2', '15_scope3', '16_scope4', '17_char', '18_init_izraz', '19_if1', '20_if2', '21_if3', '22_if4', '23_niz1', '24_niz2', '25_niz3', '26_niz4', '27_rek', '29_for', '30_while', '31_inc', '32_gcd', '33_short', '34_izraz', '35_params', '36_params2', '37_funcloop', 'integration1', 'integration2']
for test in tests:
	print(test, end = ' ')
	runTests(test)

print(str(total) + "/" + str(len(tests)))
print(str(round(total * 100 / len(tests))) + '%')