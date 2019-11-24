from Node import *
from LexicUnit import *
import sys

global terminalCharacters
global nonTerminalCharacters
global syncCharacters
global actions
global productions
global stack
global data

def move(node, value):
	stack.append(node)
	stack.append(value)

def startParsing():
	global terminalCharacters
	global nonTerminalCharacters
	global syncCharacters
	global actions
	global productions
	global stack
	global data
	counter = 0
	while len(data) > 0:

		character = data[0][0]
		currentState = stack[-1]

		# TODO OPORAVAK
		action = actions[currentState.getIndex() if type(currentState) is Node else currentState][character]
		#print(data)
		#print([i.getIndex() if type(i) is Node else i for i in stack])
		#print(currentState.getIndex() if type(currentState) is Node else currentState)
		#print(character)
		if action[0] == 'POMAKNI':
			move(Node(data[0]), Node(action[1]))
			data = data[1:]
		elif action[0] == 'REDUCIRAJ':
			production = productions[action[1] - 1] ## Be careful with index
			#print(production)
			[left, right] = production.split(' -> ')
			right = right.split(', ')

			newNode = Node(left)
			#print(right[0])
			if right[0] == '$':
				newNode.addChild(Node("$"))
				stack.append(newNode)
				#STAVI
				f = stack[-2].getIndex() if type(stack[-2]) is Node else stack[-2]
				s = newNode.getIndex() if type(newNode) is Node else newNode
				stack.append(actions[f][s][1])
				continue

			pattern = stack[-len(right)*2:]
			stack = stack[:-len(right)*2]

			pattern = pattern[:-1]
			pattern = pattern[::-2]

			for element in pattern:
				newNode.addChild(element)

			stack.append(newNode)
			if newNode.getIndex() not in nonTerminalCharacters:
				remove()
				continue
			#STAVI
			f = stack[-2].getIndex() if type(stack[-2]) is Node else stack[-2]
			s = newNode.getIndex() if type(newNode) is Node else newNode
			stack.append(actions[f][s][1])
		elif action[0] == "PRIHVATI":
			return stack[-2]

dataFile = open('data.txt', "r")
lines = dataFile.read().splitlines()
dataFile.close()

terminalCharacters = []
nonTerminalCharacters = []
syncCharacters = []
actions = {}
productions = []
n = Node(0)
stack = [n]

inputCounter = 0

for line in lines:
	if "##########" in line:
		inputCounter += 1
	elif inputCounter == 0:
		nonTerminalCharacters.append(line)
	elif inputCounter == 1:
		terminalCharacters.append(line)
	elif inputCounter == 2:
		syncCharacters.append(line)
	elif inputCounter == 3:
		action = line.split(', ')
		action[0] = int(action[0])
		if not action[0] in actions.keys():
			actions[action[0]] = {}
		actions[action[0]][action[1]] = [action[2], int(action[3]) if len(action) == 4 else -1]
	else:
		productions.append(line)

inputLines = sys.stdin.read().splitlines()
data = []
for d in inputLines:
	line = d.split(" ")
	data.append([line[0], line[1], line[2:]])

data.append(["#", "#", "#"])

startParsing().tree("")