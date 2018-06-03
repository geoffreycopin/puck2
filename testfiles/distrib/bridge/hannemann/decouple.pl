
java_import(['bridge.candidate',
	     'bridge.candidate.Screen',
             'bridge.candidate.CrossCapitalGreetingScreen',
	     'bridge.candidate.CrossCapitalInformationScreen',
	     'bridge.candidate.StarGreetingScreen',
	     'bridge.candidate.StarInformationScreen']).

declareSet(implementations,
		['printText__String',
                 'printDecor__void',
                 'printLine__void']).

declareSet(abstractions, ['Screen',
                          'CrossCapitalGreetingScreen',
			  'CrossCapitalInformationScreen',
			  'StarGreetingScreen',
			  'StarInformationScreen']).

hideSetFrom(implementations, abstractions).
%%hideScopeSetFrom(abstractions, ['bridge.candidate']).
