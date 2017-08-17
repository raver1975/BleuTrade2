package com.alphatica.genotick.population;

class DAOException extends RuntimeException {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = -5112797895442527318L;

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String s) {
        super(s);
    }
}
