
import ['decorator.candidate']

concreteComponents = ['A']
decorators = ['AwithX', 'AwithY', 'AwithZ', 
	      'AwithXY', 'AwithXYZ']

everybody = union([concreteComponents, decorators])

hide everybody from everybody
