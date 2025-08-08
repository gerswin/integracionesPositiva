package com.prolinktic.sgdea.dtos.radicado;

public class AlfrescoResponse {
    private AlfrescoEntry entry;

    public AlfrescoEntry getEntry() {
        return entry;
    }

    public static class AlfrescoEntry {
        private String name;

        // otros campos y métodos getter y setter...

        public String getName() {
            return name;
        }
    }
}
