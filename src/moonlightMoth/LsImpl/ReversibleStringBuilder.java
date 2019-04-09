package moonlightMoth.LsImpl;

class ReversibleStringBuilder
{
    private StringBuilder sb;
    private boolean isReversedAppend = false;

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

    ReversibleStringBuilder append(int i)
    {
        if (isReversedAppend) sb.insert(0, i); else sb.append(i);
        return this;
    }

    ReversibleStringBuilder append(char c)
    {
        if (isReversedAppend) sb.insert(0, c); else sb.append(c);
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
