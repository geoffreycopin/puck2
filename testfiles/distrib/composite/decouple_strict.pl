import ['fileSystem']
file_kinds = ['File', 'Directory']

r:fileSystem friend-of r:file_kinds
hide r:file_kinds but-not-from 'client.FSClient.main(String[])'
