namespace UIConfigGenerator.Attributes
{
    [AttributeUsage(AttributeTargets.Parameter | AttributeTargets.Property | AttributeTargets.Field, AllowMultiple = false)]
    public class TextFieldAttribute : Attribute
    {
        public required int MinLength { get; set; }
        public required int MaxLength { get; set; }
        public required string Pattern { get; set; }
    }
}
