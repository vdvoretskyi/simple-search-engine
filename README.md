# simple-search-engine
A simple search engine
Service should work with small documents where each document contains a series of tokens (words) separated by spaces. 
To keep things simple document can be represented as String.
The usage model of service:
1. Put documents into the search engine by key
2. Get document by key
3. Search on a string of tokens (words) to return keys of all documents that contain all tokens in the set
For index persistence documents stored in server's memory.
