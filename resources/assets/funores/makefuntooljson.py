toolClasses = ["Sword", "Pickaxe", "Shovel", "Axe", "Hoe"]

def writeJSON(filename, tool, plus, toolClass):
  f = open(filename, 'w')
  f.write('{\n')
  f.write('  "parent": "builtin/generated",\n')
  f.write('  "textures": {\n')
  f.write('    "layer0": "FunOres:items/' + name + '"\n')
  f.write('  },\n')
  f.write('  "display": {\n')
  f.write('    "thirdperson": {\n')
  f.write('      "rotation": [0, 90, -35],\n')
  f.write('      "translation": [0, 1.25, -3.5],\n')
  f.write('      "scale": [0.85, 0.85, 0.85 ]\n')
  f.write('    },\n')
  f.write('    "firstperson": {\n')
  f.write('      "rotation": [0, -135, 25 ],\n')
  f.write('      "translation": [0, 4, 2 ],\n')
  f.write('      "scale": [1.7, 1.7, 1.7]\n')
  f.write('    }\n')
  f.write('  }\n')
  f.write('}\n')
  f.write('\n')


for tool in toolClasses:
  name = tool + 'Bronze'
  writeJSON(name + '.json', name, False, tool)
  name = tool + 'Steel'
  writeJSON(name + '.json', name, False, tool)
