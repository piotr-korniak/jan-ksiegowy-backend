package pl.janksiegowy.backend.database;


public final class CompanyContext {
    private static final ThreadLocal<String> currentCompany= new ThreadLocal<>();

    public static void setCurrentCompany( String tenant) {
        currentCompany.set( tenant);
    }

    public static String getCurrentCompany() {
        return currentCompany.get();
    }

    public static void clear() {
        currentCompany.remove();
    }
}
