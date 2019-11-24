class LexicUnit:
	def __init__(self, idn, row, value):
		self.idn = idn
		self.row = row
		self.value = value
	
	def __str__(self):
		return self.idn + " " + self.row + " " + self.value