namespace UIConfigGenerator.Attributes
{
    [AttributeUsage(AttributeTargets.Parameter | AttributeTargets.Property | AttributeTargets.Field, AllowMultiple = false)]
    public class NumberFieldAttribute : Attribute
    {
        public required int Min { get; set; }
        public required int Max { get; set; }
    }
}
