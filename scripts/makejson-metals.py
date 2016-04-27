import sys
import re
import os

METALS = ['Copper', 'Tin', 'Silver', 'Lead', 'Nickel', 'Platinum', \
            'Aluminium', 'Zinc', 'Titanium', 'Bronze', 'Brass', 'Steel', \
            'Invar', 'Electrum', 'Enderium', 'Prismarinium', 'Iron', 'Gold']

MOD_ID = 'FunOres'

def createDirIfNeeded(name):
    if not os.path.exists(name):
        os.makedirs(name)

def createAllDirs():
    createDirIfNeeded('output/blockstates')
    createDirIfNeeded('output/models/block')
    createDirIfNeeded('output/models/item')

def writeBlockJSONs(name, texture):
    print('Writing block %s (texture %s)' % (name, texture))

    #blockstate
    f = open('output/blockstates/' + name + '.json', 'w')
    f.write('{\n')
    f.write('  "variants": {\n')
    f.write('    "normal": { "model": "%s:%s" }\n' % (MOD_ID, name))
    f.write('  }\n')
    f.write('}\n')
    f.close()

    #block model
    f = open('output/models/block/%s.json' % name, 'w')
    f.write('{\n')
    f.write('  "parent": "block/cube_all",\n')
    f.write('  "textures": {\n')
    f.write('    "all": "%s:blocks/%s"\n' % (MOD_ID, texture))
    f.write('  }\n')
    f.write('}\n')
    f.close()

    #item model
    f = open('output/models/item/%s.json' % name, 'w')
    f.write('{\n')
    f.write('  "parent": "%s:block/%s",\n' % (MOD_ID, name))
    f.write('  "textures": {\n')
    f.write('    "layer0": "%s:blocks/%s"\n' % (MOD_ID, texture))
    f.write('  }\n')
    f.write('}\n')
    f.close()

def writeItemJSON(name, texture, layer=0, item_type='generated'):
    print('Writing item %s (texture %s)' % (name, texture))

    f = open('output/models/item/' + name + '.json', 'w')
    f.write('{\n')
    f.write('  "parent": "item/%s",\n' % item_type)
    f.write('  "textures": {\n')
    for i in range(0, layer):
        f.write('    "layer%d": "%s:items/Blank",\n' % (i, MOD_ID))
    f.write('    "layer%d": "%s:items/%s"\n' % (layer, MOD_ID, texture))
    f.write('  }\n')
    f.write('}\n')
    f.write('\n')
    f.close()



numRegex = re.compile("^\d+$")

isBlock = False
name = ''

for arg in sys.argv:
  argl = str.lower(arg)
  if argl == 'block':
    isBlock = True
  elif argl == 'item':
    isBlock = False
  match = numRegex.match(arg)
  if match:
    pass
    #count = int(match.group(0))
  elif arg != 'makejson-metals.py':
    name = arg

if name == '':
  print('No block/item name specified!')
  exit(1)

createAllDirs()

for metal in METALS:
  s = name + metal
  if isBlock:
    writeBlockJSONs(s, s)
  else:
    writeItemJSON(s, s)
#