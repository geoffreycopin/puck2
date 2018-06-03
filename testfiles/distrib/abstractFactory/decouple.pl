import ['abstractFactory.candidate']

motifProducts = ['MotifButton.MotifButton#_void', 'MotifMenu.MotifMenu#_void']

windowsProducts = ['WindowsButton.WindowsButton#_void','WindowsMenu.WindowsMenu#_void']

products = union([motifProducts, windowsProducts])

hide products

#hide motifProducts
#hide windowsProducts

