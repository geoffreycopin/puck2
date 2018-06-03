import ['templateMethod.candidate']

concreteImplem = ['DecoratedStringGenerator.prepareFancy__String',
			    'DecoratedStringGenerator.filterFancy__String',
			    'DecoratedStringGenerator.finalizeFancy__String',
			    'DecoratedStringGenerator.prepareSimple__String',
			    'DecoratedStringGenerator.filterSimple__String',
			    'DecoratedStringGenerator.finalizeSimple__String']

%declareSet(template_method,['DecoratedStringGenerator.generate__String']).
template_method = ['DecoratedStringGenerator']

hide concreteImplem from template_method
