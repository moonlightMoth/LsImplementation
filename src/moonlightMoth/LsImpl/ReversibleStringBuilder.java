package moonlightMoth.LsImpl;

class ReversibleStringBuilder
{
    private StringBuilder sb;
    private boolean isReversedAppend;

    ReversibleStringBuilder(boolean isReversedAppend)
    {
        sb = new StringBuilder();
        this.isReversedAppend = isReversedAppend;
    }

    ReversibleStringBuilder append(String s)
    {
        if (isReversedAppend) sb.insert(0, s); else sb.append(s);
        return this;
    }

    ReversibleStringBuilder append(Object o)
    {
        if (isReversedAppend) sb.insert(0, o); else sb.append(o);
        return this;
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }
}
