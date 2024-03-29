package it.polimi.ingsw;

public class Corner {
    private boolean canCover;
    private boolean canBeCovered;
    private boolean containsSymbol;
    private boolean containsResource;
    private SymbolType symbol;
    private ResourceType resource;
    private CornerType cornerType;
    private Card linkedCard;



    public Corner(CornerType cornerType, ResourceType resourceType, SymbolType symbolType) {
        setCornerType(cornerType);
        setResource(resourceType);
        setSymbol(symbolType);
        this.linkedCard = null;

    }

    public boolean ContainsSymbol() {
        return containsSymbol;
    }

    public void setContainsSymbol(boolean containsSymbol) {
        this.containsSymbol = containsSymbol;
    }

    public boolean ContainsResource() {
        return containsResource;
    }

    public void setContainsResource(boolean containsResource) {
        this.containsResource = containsResource;
    }

    public SymbolType getSymbol() {
        return symbol;
    }

    public ResourceType getResource() {
        return resource;
    }

    /**
     * @author Margherita Marino
     * setter
     * @param resourceType
     * @throws IllegalArgumentException if the resourceType is not a valid type
     */
    public void setResource(ResourceType resourceType) {
        if (resourceType == null) {
            setContainsResource(false);
            this.resource = null;
        } else {
            switch (resourceType) {
                case ANIMALKINGDOM:
                    this.resource = ResourceType.ANIMALKINGDOM;
                    setContainsResource(true);
                    break;
                case FUNGIKINGDOM:
                    this.resource = ResourceType.FUNGIKINGDOM;
                    setContainsResource(true);
                    break;
                case PLANTKINGDOM:
                    this.resource = ResourceType.PLANTKINGDOM;
                    setContainsResource(true);
                    break;
                case INSECTKINGDOM:
                    this.resource = ResourceType.INSECTKINGDOM;
                    setContainsResource(true);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid resourceType");
            }

        }
        this.setContainsResource(true);
    }

    /**
     * @author Margherita Marino
     * setter
     * @param symbolType
     * @throws IllegalArgumentException if the symbolType is not a valid type
     */
        public void setSymbol(SymbolType symbolType) {
            if(symbolType == null){
                setContainsSymbol(false);
                this.symbol = null;
            }
            else {
                switch (symbolType) {
                    case QUILL:
                        this.symbol = SymbolType.QUILL;
                        setContainsSymbol(true);
                        break;
                    case INKWELL:
                        this.symbol = SymbolType.INKWELL;
                        setContainsSymbol(true);
                        break;
                    case MANUSCRIPT:
                        this.symbol = SymbolType.MANUSCRIPT;
                        setContainsSymbol(true);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid symbolType");
                }
            }
            this.setContainsSymbol(true);
        }

    /**
     * @author Margherita Marino
     * setter
     * @param cornerType
     * @throws IllegalArgumentException if the cornerType is not a valid type
     */
    public void setCornerType(CornerType cornerType) {
            if(cornerType == null){
                this.cornerType = null;
            }
            else {
                switch (cornerType) {
                    case TOPR:
                        this.cornerType = CornerType.TOPR;
                        break;
                    case TOPL:
                        this.cornerType = CornerType.TOPL;
                        break;
                    case LOWR:
                        this.cornerType = CornerType.LOWR;
                        break;
                    case LOWL:
                        this.cornerType = CornerType.LOWL;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid corner type");
                }
            }
    }

    public CornerType getCornerType() {
        return cornerType;
    }



    //Setta randomicamente (e senza mandare nessun parametro in ingresso) una ResourceType al corner
    public void setRandomicResource (){

    }

    //Setta randomicamente (e senza mandare nessun parametro in ingresso) un SymbolType al Corner
    public void setRandomicSymbol (){

    }

    public Card getLinkedCard() {
        return linkedCard;
    }

    public void setLinkedCard(Card linkedCard) {
        this.linkedCard = linkedCard;
    }
}