import ['adapter.candidate']

legacy_code = ['LegacyLine', 'LegacyRectangle']
adapter_ = ['AdapterDemo.drawLine__LegacyLine_int_int_int_int',
		'AdapterDemo.drawRectangle__LegacyRectangle_int_int_int_int']

client = ['AdapterDemo']

hide legacy_code from client
hide adapter_ from client
