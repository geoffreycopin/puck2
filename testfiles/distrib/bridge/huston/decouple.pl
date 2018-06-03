
% Intent : decouple abstractions from implementations 
% We have two implementations of a Stack, one using an array, the other using a List
%
% The abstractions are StackFIFO and StackHanoi
% the first one use a second stack to be able to access the last pushed elements
% the second one permits to push only elements which are smaller than the actual top
%
% They both use a Stack/LIFO object but don't need to know whether it is implemented with an array or a list

declareSet(concreteImplementations, ['bridge2.candidate.StackArray',
					'bridge2.candidate.StackList']).

declareSet(abstractImplementations, []).

declareSetUnion(implementations, [abstractImplementations, concreteImplementations]).

declareSet(plainAbstractions,[]).

declareSetUnion(abstractions, [plainAbstractions,  refinedAbstractions]).

declareSet(refinedAbstractions, ['bridge2.candidate.StackFIFO', 'bridge2.candidate.StackHanoi']).


hideScopeSetFrom(concreteImplementations, abstractions).
hideScopeSetFrom(refinedAbstractions, implementations).







