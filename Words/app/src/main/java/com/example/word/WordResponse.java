package com.example.word;

import java.util.List;

public class WordResponse {


    /**
     * word : family anomiidae
     * results : [{"definition":"saddle oysters","partOfSpeech":"noun","synonyms":["anomiidae"],"typeOf":["mollusk family"],"hasMembers":["anomia","genus anomia"],"memberOf":["class lamellibranchia","class pelecypoda","bivalvia","lamellibranchia","class bivalvia"]}]
     */

    private String word;
    private List<ResultsBean> results;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * definition : saddle oysters
         * partOfSpeech : noun
         * synonyms : ["anomiidae"]
         * typeOf : ["mollusk family"]
         * hasMembers : ["anomia","genus anomia"]
         * memberOf : ["class lamellibranchia","class pelecypoda","bivalvia","lamellibranchia","class bivalvia"]
         */

        private String definition;
        private String partOfSpeech;
        private List<String> synonyms;
        private List<String> typeOf;
        private List<String> hasMembers;
        private List<String> memberOf;

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<String> synonyms) {
            this.synonyms = synonyms;
        }

        public List<String> getTypeOf() {
            return typeOf;
        }

        public void setTypeOf(List<String> typeOf) {
            this.typeOf = typeOf;
        }

        public List<String> getHasMembers() {
            return hasMembers;
        }

        public void setHasMembers(List<String> hasMembers) {
            this.hasMembers = hasMembers;
        }

        public List<String> getMemberOf() {
            return memberOf;
        }

        public void setMemberOf(List<String> memberOf) {
            this.memberOf = memberOf;
        }
    }
}
