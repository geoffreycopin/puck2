
java_import(['bridge.candidate']).

declareSet(implementations,
		['WelcomeScreenText.printText__void',
		 'WelcomeScreenGraphic.printGraphic__void',
		 'InfoScreenText.printText__void',
		 'InfoScreenGraphic.printGraphic__void']).

declareSet(abstractions, ['Screen',
						'WelcomeScreenText', 'WelcomeScreenGraphic',
						'InfoScreenText', 'InfoScreenGraphic']).

hideSetFrom(implementations, abstractions).
%%hideScopeSetFrom(abstractions, ['bridge.candidate']).
