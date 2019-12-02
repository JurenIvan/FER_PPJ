class Node:
	def __init__(self, name, data = []):
		self.name = name
		self.data = data
		self.children = []

	def getIndex(self):
		return self.name

	def addChild(self, child):
		self.children.insert(0, child)
		
	def tree(self, spacing):
		if isinstance(self.name, list):
			print(spacing + self.name[0] + " " + self.name[1] + " " + ' '.join(self.name[2]))
		else:
			print(spacing + str(self.name))
		for child in self.children:
			child.tree(spacing + " ")