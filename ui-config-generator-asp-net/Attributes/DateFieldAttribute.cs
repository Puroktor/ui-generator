namespace UIConfigGenerator.Attributes
{
    [AttributeUsage(AttributeTargets.Parameter | AttributeTargets.Property | AttributeTargets.Field, AllowMultiple = false)]
    public class DateFieldAttribute(string dateFormat) : Attribute
    {
        public static readonly string DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

        public string DateFormat { get; set; } = dateFormat ?? DEFAULT_DATE_FORMAT;
    }
}
